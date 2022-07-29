package com.example.urlshortener.controller;

import com.example.urlshortener.model.UrlRequestDTO;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/create")
    public String createShortUrl(@RequestBody UrlRequestDTO longUrlRequest) {
        return urlService.toShortUrl(longUrlRequest);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getLongUrl(@PathVariable String shortUrl) throws URISyntaxException {
        URI longUrl = urlService.toLongUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(longUrl)
                .build();
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
