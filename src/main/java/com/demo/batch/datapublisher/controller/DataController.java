package com.demo.batch.datapublisher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/data-publisher/v1")
public class DataController {

    @GetMapping("/start")
    public String startJob() {
        return "working";
    }
}
