#!/usr/bin/env bash
set -euo pipefail

apk_path="${1:?Usage: verify_apk_signing.sh <apk-path>}"
: "${CURRENTLENS_SIGNING_KEYSTORE_PATH:?Missing configured signing keystore path}"
: "${CURRENTLENS_SIGNING_STORE_PASSWORD:?Missing configured signing store password}"
: "${CURRENTLENS_SIGNING_KEY_ALIAS:?Missing configured signing key alias}"

if [[ ! -s "$apk_path" ]]; then
  echo "::error::APK is missing or empty: $apk_path"
  exit 1
fi
if [[ ! -s "$CURRENTLENS_SIGNING_KEYSTORE_PATH" ]]; then
  echo "::error::Configured signing keystore is missing or empty"
  exit 1
fi

android_home="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
apksigner="$android_home/build-tools/37.0.0/apksigner"
if [[ ! -x "$apksigner" ]]; then
  apksigner="$(command -v apksigner || true)"
fi
if [[ -z "$apksigner" || ! -x "$apksigner" ]]; then
  echo "::error::apksigner was not found in the installed Android build-tools"
  exit 1
fi

certificate_file="$(mktemp)"
trap 'rm -f "$certificate_file"' EXIT
keytool -exportcert \
  -keystore "$CURRENTLENS_SIGNING_KEYSTORE_PATH" \
  -storepass "$CURRENTLENS_SIGNING_STORE_PASSWORD" \
  -alias "$CURRENTLENS_SIGNING_KEY_ALIAS" \
  -file "$certificate_file" >/dev/null

expected_sha256="$(sha256sum "$certificate_file" | awk '{print tolower($1)}')"
pinned_sha256="${CURRENTLENS_SIGNING_EXPECTED_SHA256:-}"
if [[ -n "$pinned_sha256" ]]; then
  pinned_sha256="$(printf '%s' "$pinned_sha256" | tr '[:upper:]' '[:lower:]')"
  if [[ ! "$pinned_sha256" =~ ^[0-9a-f]{64}$ || "$expected_sha256" != "$pinned_sha256" ]]; then
    echo "::error::Configured signing keystore does not match the pinned release certificate fingerprint"
    exit 1
  fi
fi
verification_output="$("$apksigner" verify --print-certs "$apk_path")"
signer_count="$(printf '%s\n' "$verification_output" | grep -Ec 'Signer #[0-9]+ certificate SHA-256 digest:' || true)"
actual_sha256="$(printf '%s\n' "$verification_output" | awk -F': ' '/Signer #[0-9]+ certificate SHA-256 digest:/ {print tolower($2); exit}')"

if [[ "$signer_count" != "1" || -z "$actual_sha256" ]]; then
  echo "::error::APK must have exactly one verifiable signer certificate"
  exit 1
fi
if [[ "$expected_sha256" != "$actual_sha256" ]]; then
  echo "::error::APK signer does not match the configured stable keystore"
  exit 1
fi

printf 'APK signer matches configured keystore (SHA-256: %s)\n' "$actual_sha256"
