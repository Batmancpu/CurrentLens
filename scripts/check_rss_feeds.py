"""Bounded, read-only publisher RSS smoke check; never a release gate."""
from __future__ import annotations

import concurrent.futures
import os
import re
import socket
import ssl
import urllib.error
import urllib.parse
import urllib.request
import xml.etree.ElementTree as ET
from pathlib import Path

MAX_BYTES = 2 * 1024 * 1024
MAX_REDIRECTS = 5
DOMAINS = {
    "The Hindu": ("thehindu.com",),
    "Indian Express": ("indianexpress.com",),
    "BBC": ("bbc.com", "bbc.co.uk", "bbci.co.uk"),
    "DW": ("dw.com",),
    "Al Jazeera": ("aljazeera.com",),
}


def allowed(publisher: str, url: str) -> bool:
    try:
        parsed = urllib.parse.urlsplit(url)
        host = (parsed.hostname or "").lower().rstrip(".")
        return (parsed.scheme == "https" and parsed.username is None
                and parsed.password is None and parsed.port in (None, 443)
                and any(host == domain or host.endswith("." + domain)
                        for domain in DOMAINS.get(publisher, ())))
    except ValueError:
        return False


class UnsafeRedirect(Exception):
    pass


class PublisherRedirect(urllib.request.HTTPRedirectHandler):
    def __init__(self, publisher: str):
        self.publisher = publisher
        self.hops = 0

    def redirect_request(self, req, fp, code, msg, headers, newurl):
        self.hops += 1
        if self.hops > MAX_REDIRECTS or not allowed(self.publisher, newurl):
            raise UnsafeRedirect()
        return super().redirect_request(req, fp, code, msg, headers, newurl)


def configured_feeds():
    root = Path(__file__).resolve().parent.parent
    source = root / "app/src/main/java/com/mangoloads/currentlens/NewsRepository.kt"
    pattern = r'FeedSource\("([^"]+)",\s*"([^"]+)",\s*"([^"]+)"'
    feeds = re.findall(pattern, source.read_text(encoding="utf-8"))
    if not feeds or any(not allowed(publisher, url) for publisher, _, url in feeds):
        raise ValueError("Missing or unsafe feed configuration")
    return feeds


def count_entries(body: bytes) -> int:
    if len(body) > MAX_BYTES:
        raise ValueError("Response exceeds limit")
    if b"<!DOCTYPE" in body.upper() or b"<!ENTITY" in body.upper():
        raise ValueError("DTD not accepted")
    root = ET.fromstring(body)
    local_name = lambda tag: tag.rsplit("}", 1)[-1].lower()
    if local_name(root.tag) not in ("rss", "feed", "rdf"):
        raise ValueError("Not RSS or Atom")
    return sum(local_name(element.tag) in ("item", "entry") for element in root.iter())


def probe(feed):
    publisher, section, url = feed
    if not allowed(publisher, url):
        return publisher, section, "unsafe configured URL", 0
    opener = urllib.request.build_opener(PublisherRedirect(publisher))
    request = urllib.request.Request(url, headers={
        "User-Agent": "CurrentLens/0.2 RSS reader",
        "Accept": "application/rss+xml, application/atom+xml, application/xml, text/xml",
    })
    try:
        with opener.open(request, timeout=8) as response:
            if not allowed(publisher, response.geturl()):
                raise UnsafeRedirect()
            body = response.read(MAX_BYTES + 1)
        return publisher, section, "parsed", count_entries(body)
    except UnsafeRedirect:
        return publisher, section, "blocked redirect", 0
    except urllib.error.HTTPError as error:
        return publisher, section, f"HTTP {error.code}", 0
    except (socket.timeout, TimeoutError):
        return publisher, section, "timeout", 0
    except ssl.SSLError:
        return publisher, section, "TLS error", 0
    except (ET.ParseError, ValueError):
        return publisher, section, "invalid or oversized XML", 0
    except (OSError, urllib.error.URLError):
        return publisher, section, "network error", 0


def main():
    feeds = configured_feeds()
    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
        results = list(executor.map(probe, feeds))
    lines = ["## Live RSS smoke check (CI network; not handset evidence)",
             "", "| Publisher / section | Result | Raw entries |",
             "| --- | --- | ---: |"]
    for publisher, section, status, count in results:
        lines.append(f"| {publisher} / {section} | {status} | {count} |")
    lines.append("")
    lines.append("This checks endpoint and XML availability only. It does not measure app eligibility, RSS date validity, or device connectivity.")
    report = "\n".join(lines) + "\n"
    print(report)
    summary = os.environ.get("GITHUB_STEP_SUMMARY")
    if summary:
        with open(summary, "a", encoding="utf-8") as file:
            file.write(report)


if __name__ == "__main__":
    main()
