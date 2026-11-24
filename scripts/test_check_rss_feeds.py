import unittest
from check_rss_feeds import allowed, configured_feeds, count_entries, MAX_BYTES


class FeedSmokeTest(unittest.TestCase):
    def test_config_uses_known_publisher_https(self):
        feeds = configured_feeds()
        self.assertEqual(14, len(feeds))
        self.assertTrue(all(allowed(publisher, url) for publisher, _, url in feeds))

    def test_redirect_boundaries(self):
        self.assertTrue(allowed("BBC", "https://feeds.bbci.co.uk/world"))
        for url in ("http://bbc.com/feed", "https://bbc.com.evil.test/feed",
                    "https://user@bbc.com/feed", "https://bbc.com:444/feed"):
            self.assertFalse(allowed("BBC", url))

    def test_xml_counts_and_rejects_other_documents(self):
        self.assertEqual(2, count_entries(b"<rss><channel><item/><item/></channel></rss>"))
        self.assertEqual(1, count_entries(b'<feed xmlns="http://www.w3.org/2005/Atom"><entry/></feed>'))
        self.assertEqual(0, count_entries(b"<rss><channel/></rss>"))
        for invalid in (b"<html/>", b"<!DOCTYPE rss><rss/>", b"x" * (MAX_BYTES + 1)):
            with self.assertRaises(ValueError):
                count_entries(invalid)


if __name__ == "__main__":
    unittest.main()
