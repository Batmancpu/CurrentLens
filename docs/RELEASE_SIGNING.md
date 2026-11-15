# CurrentLens signed APK releases

The owner chose a fresh install on 14 November 2026. The new release line uses application ID `com.mangoloads.currentlens`, begins at versionCode 11, and has one permanent APK signing identity. Old testing APKs and the separate Stability package have their own signing history. Install the first new APK as a fresh install. Use the subsequent APKs as in-place updates.

## Latest verified release

- [CurrentLens 0.3.4 (`android-15`)](https://github.com/Batmancpu/CurrentLens/releases/tag/android-15), published from exact main source `276191f0656261a8f7dfab13847a3e654ca64245` by [run #426](https://github.com/Batmancpu/CurrentLens/actions/runs/43728047667).
- One asset: `CurrentLens.apk`, 30,830,942 bytes; SHA-256 `8a07dd4ab61e51388bd728375defccfd094de1d9b17cf4415457a0cd924a6d4a`.
- Package/version: `com.mangoloads.currentlens`, 0.3.4, versionCode 15. Pinned signing certificate SHA-256 `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`.
- CI passed dictionary generation, Python builder tests, `:app:testDebugUnitTest`, `:app:assembleRelease`, package/version checks, signature verification and publication. Physical-device install/update and behavior are not implied.

## One-time key custody

Run on a computer with Java and an authenticated GitHub CLI account that can write Actions secrets for Batmancpu/CurrentLens. Pick a secure folder outside the checkout. Keep an encrypted backup of the keystore and save the password in a password manager.

```bash
bash scripts/provision_release_signing.sh /absolute/private/path/currentlens-release.p12
```

The command generates or reuses the key locally and provisions four encrypted repository Actions secrets plus the public `CURRENTLENS_RELEASE_CERT_SHA256` variable. The key-password secret is optional when both passwords match, as they do for the generated PKCS#12. The workflow then uses the store password for both. It reads the password silently and refuses a different key once a certificate is pinned. If interrupted after the certificate is pinned, rerun using the same keystore and password. Keep the keystore, password and Base64 representation out of chat, Git history, Actions caches and release artifacts. [Android signing guidance](https://developer.android.com/studio/publish/app-signing) · [GitHub Actions secrets](https://docs.github.com/en/actions/concepts/security/secrets).

## Build and publish

1. Let the automatic main build complete successfully. It validates the source and assembles the release variant.
2. Once the key is provisioned, run **CurrentLens Android** on **main** with **Publish the signed APK from main** selected. With authenticated CLI access, run `gh workflow run build-apk.yml --repo Batmancpu/CurrentLens --ref main -f publish=true`. Keep one workflow active at a time.
3. Verify the exact main SHA and successful publish job. Download the single **CurrentLens.apk** from the release tagged `android-<versionCode>`. The release notes give the APK hash and certificate fingerprint. Signing fails if the fingerprint, package or version is wrong.
4. Install once. For subsequent releases increment `versionCode` in `app/build.gradle.kts`, preserve the package and signing key, and verify update-in-place with saved local data. Keep the previous release for diagnosis. A corrected forward release can revert source with a higher version code.

The signing step runs after an unsigned build in a separate job. Only that step receives the secret material. The workflow's build and release concurrency group retains one running workflow. The release job uses the tested artifact from the same run. Device acceptance is recorded separately in the session ledger.
