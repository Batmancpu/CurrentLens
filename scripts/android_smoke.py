"""Runtime checks against an isolated, fresh debug emulator, using synthetic data only."""
import datetime as dt
import hashlib
import json
import os
import re
import subprocess
import time
import xml.etree.ElementTree as ET

PACKAGE = "com.mangoloads.currentlens"
ACTIVITY = PACKAGE + "/.MainActivity"
URL = "https://www.thehindu.com/news/national/currentlens-ci-fixture/article12345678.ece"
TITLE = "RBI monetary policy and inflation test fixture"

def adb(*args, data=None, timeout=25, check=True):
    return subprocess.run(["adb", "-s", "emulator-5554", *args], input=data,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                          timeout=timeout, check=check).stdout.decode("utf-8", errors="replace").strip()

def shell(*args, **kwargs):
    return adb("shell", *args, **kwargs)

def dump():
    shell("uiautomator", "dump", "--compressed", "/sdcard/currentlens-ci.xml", timeout=20)
    return ET.fromstring(shell("cat", "/sdcard/currentlens-ci.xml"))

def nodes_with(root, label, prefix=False):
    for node in root.iter("node"):
        if node.get("package") != PACKAGE:
            continue
        fields = (node.get("text", ""), node.get("content-desc", ""))
        if any(x.startswith(label) if prefix else x == label for x in fields):
            yield node

def await_node(label, prefix=False, seconds=45):
    deadline = time.monotonic() + seconds
    while time.monotonic() < deadline:
        try:
            root = dump()
            found = next(nodes_with(root, label, prefix), None)
            if found is not None:
                return found
        except (ET.ParseError, subprocess.SubprocessError):
            pass
        time.sleep(1)
    raise AssertionError("Expected UI not found: " + label)

def tap(label, prefix=False):
    node = await_node(label, prefix)
    x1,y1,x2,y2 = map(int, re.findall(r"\d+", node.get("bounds", "")))
    assert x2 > x1 and y2 > y1, "Empty target bounds"
    shell("input", "tap", str((x1+x2)//2), str((y1+y2)//2))
    time.sleep(1)

def verify_launch():
    await_node("You")
    assert shell("pidof", PACKAGE), "App process not alive"
    deadline = time.monotonic() + 15
    while time.monotonic() < deadline:
        stage = shell("run-as", PACKAGE, "cat", "files/currentlens-last-startup-stage.txt", check=False)
        if "First Compose frames completed" in stage:
            return
        time.sleep(1)
    raise AssertionError("Healthy Compose startup checkpoint not reached")

def start():
    result = shell("am", "start", "-W", "-n", ACTIVITY, timeout=45)
    assert "Error:" not in result, "Activity failed to start"
    verify_launch()

def write_private(path, content):
    adb("shell", "-T", "run-as " + PACKAGE + " sh -c 'cat > " + path + "'", data=content)

def seed():
    shell("am", "force-stop", PACKAGE)
    shell("run-as", PACKAGE, "mkdir", "-p", "shared_prefs", "files/reader_article_cache")
    now = dt.datetime.now(dt.timezone.utc)
    article = dict(id=URL, title=TITLE, description="Synthetic test of inflation and RBI monetary policy.",
                   url=URL, source="The Hindu", section="Economy", published=now.isoformat(), weekendRead=False)
    quiz = dict(id="ci-quiz", articleUrl=URL, articleTitle=TITLE, subject="Economy",
                gsPaper="GS III", totalQuestions=3, answeredCount=3, correctCount=2,
                submittedAtMillis=int(now.timestamp()*1000), answers=[])
    root = ET.Element("map")
    saved = ET.SubElement(root, "set", name="bookmarks")
    ET.SubElement(saved, "string").text=URL
    ET.SubElement(root, "string", name="article_cache").text=json.dumps([article])
    ET.SubElement(root, "string", name="quiz_attempts").text=json.dumps([quiz])
    write_private("shared_prefs/currentlens_local.xml", ET.tostring(root, encoding="utf-8", xml_declaration=True))
    key = hashlib.sha256(URL.encode()).hexdigest()
    write_private("files/reader_article_cache/"+key+".html", b"<p>Offline synthetic reader content.</p>"*12)

def report(label):
    print("PASS: " + label, flush=True)
    if os.environ.get("GITHUB_STEP_SUMMARY"):
        with open(os.environ["GITHUB_STEP_SUMMARY"], "a", encoding="utf-8") as f:
            f.write("- PASS: " + label + "\n")

def main():
    assert os.environ.get("GITHUB_ACTIONS") == "true" and os.environ.get("ANDROID_SERIAL") == "emulator-5554"
    # First clean launch, then restored local state under network loss.
    start()
    report("Clean cold launch and first Compose frames")
    seed()
    shell("cmd", "connectivity", "airplane-mode", "enable")
    shell("svc", "wifi", "disable")
    shell("svc", "data", "disable")
    for n in range(4):
        shell("am", "force-stop", PACKAGE)
        start()
        report("Restored-state cold launch " + str(n+1))
    shell("input", "keyevent", "KEYCODE_HOME")
    start()
    report("Warm return")
    tap("You")
    tap("Saved", prefix=True)
    tap(TITLE)
    await_node("Back to article list")
    await_node("Text size,", prefix=True)
    report("Saved article reopens in Reader")
    tap("Back to article list")
    await_node("SAVED ARTICLES")
    report("Reader return preserves Saved")
    shell("input", "keyevent", "KEYCODE_BACK")
    await_node("You")
    tap("Search")
    await_node("Search articles")
    shell("input", "text", "inflation")
    await_node("inflation")
    report("Search field accepts keyboard input")
    shell("input", "keyevent", "KEYCODE_BACK")
    shell("am", "start", "-W", "-n", PACKAGE+"/.DictionaryActivity",
          "-a", "android.intent.action.PROCESS_TEXT", "--es", "android.intent.extra.PROCESS_TEXT", "democracy")
    await_node("democracy")
    await_node("Show more")
    report("Offline dictionary returned a definition")
    # Database should have been copied and opened; verify its private file without dumping contents.
    deadline=time.monotonic()+30
    while time.monotonic()<deadline:
        if shell("run-as", PACKAGE, "sh", "-c", "'test -s no_backup/currentlens_wordnet_2025_plus.db && echo ready'", check=False)=="ready":
            report("Offline bundled dictionary database installed")
            break
        time.sleep(1)
    else:
        raise AssertionError("Offline dictionary database not installed")
    shell("input", "keyevent", "KEYCODE_BACK")
    assert shell("pidof", PACKAGE), "App process lost during smoke"
    report("Runtime smoke complete (emulator only)")

if __name__ == "__main__":
    main()
