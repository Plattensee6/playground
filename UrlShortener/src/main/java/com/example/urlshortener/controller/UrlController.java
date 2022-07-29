package com.example.urlshortener.controller;

import com.example.urlshortener.model.LongUrlRequest;
import com.example.urlshortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URL;

@RestController
@RequestMapping("/api/")
@Slf4j
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/url/encode")
    public String createShortUrl(@RequestBody LongUrlRequest longUrlRequest) {
        return urlService.toShortUrlCustom(longUrlRequest);
    }

    @GetMapping("/url/{shortUrl}")
    public ResponseEntity<Void> getLongUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.toLongUrl(shortUrl);
        log.info(longUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
    @GetMapping("/url/welcome")
    public String welcome(){
        return "welcome";
    }
}
