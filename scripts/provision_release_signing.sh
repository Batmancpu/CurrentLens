#!/usr/bin/env bash
set -euo pipefail
umask 077

repository="Batmancpu/CurrentLens"
alias_name="currentlens-release"
keystore="${1:?Usage: bash scripts/provision_release_signing.sh /secure/path/currentlens-release.p12}"
[[ "$keystore" = /* ]] || { echo "Use an absolute path outside the source checkout." >&2; exit 1; }

gh auth status --hostname github.com
if ! gh api --hostname github.com "repos/$repository/actions/secrets?per_page=100" --paginate --jq '.secrets[].name' >/dev/null; then
    echo "GitHub access to the repository's Actions secrets is required." >&2
    exit 1
fi

existing_fingerprint="$(gh variable list --repo "$repository" --json name,value \
    --jq '.[] | select(.name == "CURRENTLENS_RELEASE_CERT_SHA256") | .value')"
if [[ ! -f "$keystore" && -n "$existing_fingerprint" ]]; then
    echo "A release certificate is already pinned. Restore its original keystore." >&2
    exit 1
fi

read -r -s -p "Keystore password (save it in your password manager): " signing_password
printf '\n'
[[ ${#signing_password} -ge 16 ]] || { echo "Use at least 16 characters." >&2; exit 1; }
export CURRENTLENS_SIGNING_STORE_PASSWORD="$signing_password"
unset signing_password

if [[ ! -f "$keystore" ]]; then
    keytool -genkeypair -noprompt -keystore "$keystore" -storetype PKCS12 \
        -storepass:env CURRENTLENS_SIGNING_STORE_PASSWORD -alias "$alias_name" \
        -keyalg RSA -keysize 3072 -validity 10000 -dname "CN=CurrentLens Release"
fi

fingerprint="$(keytool -exportcert -keystore "$keystore" \
    -storepass:env CURRENTLENS_SIGNING_STORE_PASSWORD -alias "$alias_name" | sha256sum | cut -d' ' -f1)"
if [[ -n "$existing_fingerprint" && "${existing_fingerprint,,}" != "$fingerprint" ]]; then
    echo "The key does not match the pinned release certificate." >&2
    exit 1
fi

gh variable set CURRENTLENS_RELEASE_CERT_SHA256 --repo "$repository" --body "$fingerprint"
base64 < "$keystore" | tr -d '\n' | gh secret set CURRENTLENS_RELEASE_KEYSTORE_BASE64 --repo "$repository"
printf '%s' "$CURRENTLENS_SIGNING_STORE_PASSWORD" | gh secret set CURRENTLENS_RELEASE_STORE_PASSWORD --repo "$repository"
printf '%s' "$CURRENTLENS_SIGNING_STORE_PASSWORD" | gh secret set CURRENTLENS_RELEASE_KEY_PASSWORD --repo "$repository"
printf '%s' "$alias_name" | gh secret set CURRENTLENS_RELEASE_KEY_ALIAS --repo "$repository"
unset CURRENTLENS_SIGNING_STORE_PASSWORD

printf 'CurrentLens release certificate SHA-256: %s\n' "$fingerprint"
printf 'Keep %s and an encrypted backup.\n' "$keystore"
