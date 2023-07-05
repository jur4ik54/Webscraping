package com.keycon.spring.scraping.controller;

import com.keycon.spring.scraping.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scrap")
public class ScrapingController {

    @Autowired
    PortalService scrap;

    @RequestMapping("html")
    public void html() {
        scrap.html();
    }

    @RequestMapping("all")
    public void all() {
        scrap.all();
    }

    @RequestMapping("title")
    public void allTitle() {
        scrap.allTitle();
    }

}

