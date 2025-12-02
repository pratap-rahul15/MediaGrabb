package com.scrapping.MediaGrab.service;

import com.scrapping.MediaGrab.model.ScrapeResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {

    // URL origin
    public ScrapeResponse webScraper(String url) throws IOException {

        // To scrape, first we need to bring the whole page as an object here(doc).
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36") // FIX: Real browser UA
                .timeout(10000) // increased timeout
                .ignoreContentType(true) // FIX: handle JSON/script pages
                .get();

        List<String> images = new ArrayList<>();
        List<String> videos = new ArrayList<>();
        List<String> links = new ArrayList<>();

        // ------------------------- IMAGES -------------------------
        for (Element img : doc.select("img")) {

            // Try multiple possible attributes
            String src = img.absUrl("src");
            if (src.isEmpty()) src = img.absUrl("data-src");
            if (src.isEmpty()) src = img.absUrl("data-lazy-src");
            if (src.isEmpty()) src = img.absUrl("data-original");

            // Handle 'srcset'
            if (src.isEmpty()) {
                String srcset = img.attr("srcset");
                if (!srcset.isEmpty()) {
                    src = srcset.split(" ")[0]; // take the first image URL
                }
            }

            if (!src.isEmpty()) {
                images.add(src);
            }
        }

        // ------------------------- VIDEOS -------------------------
        for (Element vid : doc.select("video")) {
            String src = vid.absUrl("src");
            if (!src.isEmpty()) {
                videos.add(src);
            }
        }

        // ------------------------- LINKS -------------------------
        for (Element link : doc.select("a[href]")) {
            String href = link.absUrl("href");
            if (!href.isEmpty()) {
                links.add(href);
            }
        }

        // Prepare response
        ScrapeResponse response = new ScrapeResponse();
        response.setImages(images);
        response.setVideos(videos);
        response.setLinks(links);

        return response;
    }
}
