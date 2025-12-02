package com.scrapping.MediaGrab.controller;

import com.scrapping.MediaGrab.model.ScrapeResponse;
import com.scrapping.MediaGrab.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class ScrapeController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping("/api/scrape")
    public ScrapeResponse scrape(@RequestParam String url) throws IOException {
        // Calling this method from the service class.
        return  scraperService.webScraper(url);
    }
}
