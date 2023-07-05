package com.keycon.spring.scraping.controller;

import com.keycon.spring.scraping.model.JobEntity;
import com.keycon.spring.scraping.service.PortalDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    PortalDBService portalDBService;

    @RequestMapping("all")
    public List<JobEntity> getAll() {
        return portalDBService.getAll();
    }

}
