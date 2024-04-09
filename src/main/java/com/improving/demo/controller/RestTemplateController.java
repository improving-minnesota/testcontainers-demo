package com.improving.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/rest")
public class RestTemplateController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${http.rest.testing-url}")
    private String testingUrl;

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> makeDeleteRequest() {
        var response = restTemplate.exchange(URI.create(testingUrl + "/delete"), HttpMethod.DELETE, null, String.class);
        return ResponseEntity.status(response.getStatusCode().value()).build();
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Void> makeGetRequest() {
        var response = restTemplate.exchange(URI.create(testingUrl + "/get?param1=foo&param2=bar"), HttpMethod.GET, null, String.class);
        return ResponseEntity.status(response.getStatusCode().value()).build();
    }

    @PostMapping(value = "/post")
    public ResponseEntity<Void> makePostRequest() {
        var body = new HttpEntity<>("body test string goes here");
        var response = restTemplate.exchange(URI.create(testingUrl + "/post"), HttpMethod.POST, body, String.class);
        return ResponseEntity.status(response.getStatusCode().value()).build();
    }

    @PutMapping(value = "/put")
    public ResponseEntity<Void> makePutRequest() {
        var body = new HttpEntity<>("body test string goes here");
        var response = restTemplate.exchange(URI.create(testingUrl + "/put"), HttpMethod.PUT, body, String.class);
        return ResponseEntity.status(response.getStatusCode().value()).build();
    }
}
