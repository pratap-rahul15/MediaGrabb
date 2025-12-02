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
        Document doc = Jsoup.connect(url).get();

        List<String> images = new ArrayList<>();
        List<String> videos = new ArrayList<>();
        List<String> links = new ArrayList<>();

        for(Element img:doc.select("img")) {
            String src = img.absUrl("src");
            if(!src.isEmpty()) {
                images.add((src));
            }
        }

        for(Element img:doc.select("video")) {
            String src = img.absUrl("src");
            if(!src.isEmpty()) {
                videos.add((src));
            }
        }

        for(Element link:doc.select("a[href]")) {
            String href = link.absUrl("href");
            if(!href.isEmpty()) {
                links.add((href));
            }
        }

        ScrapeResponse response = new ScrapeResponse();

        response.setImages(images);
        response.setVideos(videos);
        response.setLinks(links);

        return response;
    }


}
