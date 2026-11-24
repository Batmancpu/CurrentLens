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
# Start ADB before the emulator so the console port is registered by the daemon.
adb start-server
"$ANDROID_HOME/emulator/emulator" -accel-check
"$ANDROID_HOME/emulator/emulator" -version
"$ANDROID_HOME/emulator/emulator" -avd currentlens-ci -no-window -no-audio -no-boot-anim -no-snapshot -port 5554 -gpu swiftshader -memory 3072 >"$RUNNER_TEMP/currentlens-emulator.log" 2>&1 &
emulator_pid=$!
trap 'adb -s emulator-5554 emu kill >/dev/null 2>&1 || true; kill "$emulator_pid" >/dev/null 2>&1 || true' EXIT
export ANDROID_SERIAL=emulator-5554
for _ in $(seq 1 90); do
  if ! kill -0 "$emulator_pid" 2>/dev/null; then
    echo "Emulator exited before ADB connected"
    tail -n 60 "$RUNNER_TEMP/currentlens-emulator.log"
    exit 1
  fi
  if [[ "$(adb -s emulator-5554 get-state 2>/dev/null || true)" == device ]]; then break; fi
  sleep 2
done
if [[ "$(adb -s emulator-5554 get-state 2>/dev/null || true)" != device ]]; then
  echo "ADB transport did not become available"
  adb devices
  tail -n 60 "$RUNNER_TEMP/currentlens-emulator.log"
  exit 1
fi
for _ in $(seq 1 90); do
  if [[ "$(adb shell getprop sys.boot_completed | tr -d '\r')" == 1 ]]; then break; fi
  sleep 2
done
if [[ "$(adb shell getprop sys.boot_completed | tr -d '\r')" != 1 ]]; then
  echo "Emulator boot did not complete"
  tail -n 60 "$RUNNER_TEMP/currentlens-emulator.log"
  exit 1
fi
adb shell input keyevent 82
gradle --no-daemon :app:assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
python3 scripts/android_smoke.py
