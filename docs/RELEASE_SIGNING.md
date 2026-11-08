# CurrentLens APK release signing

## Why this is required

The main-branch workflow builds app-debug.apk without an explicit signing configuration. On GitHub-hosted runners this falls back to the runner's local Android debug keystore; that key is not a repository-owned release identity.

This was verified from two real main-branch Actions artifacts:

| Main run | Commit | Artifact | APK signer certificate SHA-256 |
|---|---|---|---|
| [#112](https://github.com/Batmancpu/CurrentLens/actions/runs/42208915733) | 3cf4a1e9368cc6c74be6bb2e7711ffa113149c30 | 13497753209 | dba85a1c1ac6b3a7b829850353724e7a847e2fa456a13ab3f983d66c245f30dd |
| [#116](https://github.com/Batmancpu/CurrentLens/actions/runs/42209037417) | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 13497522975 | 28c1fc47020366d8ad1fd689935039b7b3c8b79675e93112a68e2e47452adb64 |

Both builds used applicationId=com.mangoloads.currentlens and versionCode=2. The certificate fingerprints differ. Android therefore cannot install one over the other as an in-place update. The current main release chain is already broken.

The integration branch's cached Stability key does not solve main-release continuity: stabilityTest=true is set only on non-main builds, and those builds use the separate .stability application ID.

## Required one-time setup

Create one private PKCS#12 keystore and keep an offline encrypted backup. Never regenerate it after publishing the first APK signed with it.

Example (use a strong password from a password manager; do not paste the real password into chat or commit it):

    keytool -genkeypair -noprompt \
      -keystore currentlens-release.p12 \
      -storetype PKCS12 \
      -storepass "$CURRENTLENS_RELEASE_PASSWORD" \
      -keypass "$CURRENTLENS_RELEASE_PASSWORD" \
      -alias currentlens-release \
      -keyalg RSA -keysize 3072 -validity 10000 \
      -dname "CN=CurrentLens Release,O=CurrentLens,C=IN"

    base64 -w 0 currentlens-release.p12

Add these repository Actions secrets:

- CURRENTLENS_RELEASE_KEYSTORE_BASE64 — the base64 output, with no extra text.
- CURRENTLENS_RELEASE_STORE_PASSWORD — keystore password.
- CURRENTLENS_RELEASE_KEY_ALIAS — currentlens-release (or the alias actually generated).
- CURRENTLENS_RELEASE_KEY_PASSWORD — private-key password. For the PKCS#12 example above, use the same value as the store password.

The main-branch workflow fails closed if any secret is missing or the keystore/alias cannot be opened. Non-main Stability builds continue using their separate cached test key and .stability package suffix. Every uploaded APK is checked with apksigner against the certificate exported from the configured keystore; a mismatch blocks artifact upload and Release publication.

## Migration warning — owner decision required

The private keys for the already-published ephemeral debug signatures cannot be recovered from APK certificates. The new stable key cannot update an installation signed by build #112 or #116. A first stable-key APK will require uninstall/reinstall for users on those builds.

The manifest has android:allowBackup=false; uninstalling erases app-private Saved items, read history, quiz history and other local data. This PR does not invent a data migration path. Do not merge or publish the first stable-key build until the owner has acknowledged this one-time incompatibility and decided how any wanted local data will be preserved.

After the first stable-key APK is installed, all future main-branch APKs must use the same keystore and alias. Loss or regeneration of that private key breaks updates again.
Release secrets are injected only into the step gated to a push on main. Pull-request builds do not receive the main release secrets; they use only the separate Stability test keystore.
Also set the repository Actions variable CURRENTLENS_RELEASE_CERT_SHA256 to the lowercase SHA-256 fingerprint of the DER certificate exported from this keystore. The main workflow compares the keystore to this pinned public fingerprint before Gradle runs, and the post-build verifier compares the APK signer to both. This catches an accidental or unauthorised keystore replacement instead of silently publishing an incompatible update.
### CI verifier behaviour

Run #317's first signer-check attempt failed closed after the APK assembled successfully because the verifier did not match the captured apksigner output. The follow-up captures both stdout and stderr and accepts SHA-256/SHA256 label variants. Treat the next natural PR run as the verification point; the failed run did not upload an APK and did not publish a Release.
## 8 November 2026 — signer output format and secret scope

- PR #39 run #318 passed WordNet generation, JVM unit tests and APK assembly, then failed closed in the signer parser. Actual apksigner output uses the prefix 'V2 Signer: certificate SHA-256 digest:' rather than the assumed 'Signer #1 certificate SHA-256 digest:'.
- This is a parser-format mismatch, not a certificate mismatch. The verifier now extracts all SHA-256 certificate lines independent of signer-prefix format, deduplicates fingerprints across signing schemes, and requires exactly one distinct 64-hex signer. The next natural PR run is the verification point.
- The workflow now passes private store/key passwords only to the main Gradle build and main verifier steps (or the non-main test steps); passwords are no longer persisted through GITHUB_ENV for artifact-upload actions.
## 8 November 2026 — PR #39 exact-head validation

- Natural Actions run #319 on exact head 31ee6d08d825fae0980b046f407a5dd5e3152bf1 passed. WordNet builder checks, :app:testDebugUnitTest, :app:assembleDebug, the Stability APK signer-to-keystore comparison and artifact upload all succeeded; the main release-signing steps and Release job were correctly skipped on pull_request.
- Verified test signer SHA-256: bde1edc88fc1c9bc0b3a060293dc0889262a22378a41ea5fe252532a49004d14.
- Artifact #13607833092: 39,115,994 bytes; expires 22 November 2026 07:02 UTC. The APK is the separate .stability package, not a main-release APK.
- The main secret-backed release path remains unverified until the owner configures the four secrets and public fingerprint variable, then a main push runs naturally. No main push or Release was performed.
To compute the public repository variable CURRENTLENS_RELEASE_CERT_SHA256 from the generated keystore, export its DER certificate and hash it:

    keytool -exportcert -keystore currentlens-release.p12 -storepass "$CURRENTLENS_RELEASE_PASSWORD" -alias currentlens-release | sha256sum | awk '{print $1}'