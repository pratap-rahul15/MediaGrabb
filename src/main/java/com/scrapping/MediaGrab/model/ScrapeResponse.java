package com.scrapping.MediaGrab.model;

import lombok.Data;

import java.util.List;

@Data
public class ScrapeResponse {

    private List<String> images;
    private List<String> videos;
    private List<String> links;
}
