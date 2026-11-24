#!/usr/bin/env bash
set -euo pipefail
# Ephemeral GitHub runner only. Never use a connected personal device.
if [[ "${GITHUB_ACTIONS:-}" != true || ! -e /dev/kvm ]]; then
  echo "Runtime check unavailable: hosted KVM runner required"
  exit 1
fi
sudo chmod 666 /dev/kvm
sdkmanager "emulator" "system-images;android-35;google_apis;x86_64" >/dev/null
echo no | avdmanager create avd --force --name currentlens-ci --package "system-images;android-35;google_apis;x86_64" >/dev/null
"$ANDROID_HOME/emulator/emulator" -avd currentlens-ci -no-window -no-audio -no-boot-anim -no-snapshot -gpu swiftshader_indirect -memory 3072 >"$RUNNER_TEMP/currentlens-emulator.log" 2>&1 &
emulator_pid=$!
trap 'adb -s emulator-5554 emu kill >/dev/null 2>&1 || true; kill "$emulator_pid" >/dev/null 2>&1 || true' EXIT
export ANDROID_SERIAL=emulator-5554
timeout 180 adb wait-for-device
for _ in $(seq 1 90); do
  if [[ "$(adb shell getprop sys.boot_completed | tr -d '\r')" == 1 ]]; then break; fi
  sleep 2
done
[[ "$(adb shell getprop sys.boot_completed | tr -d '\r')" == 1 ]]
adb shell input keyevent 82
gradle --no-daemon :app:assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
python3 scripts/android_smoke.py
