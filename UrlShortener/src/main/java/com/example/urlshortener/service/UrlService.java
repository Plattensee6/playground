package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlRequestDTO;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
    /*@Autowired
    private UrlConversionService urlConversionService;
    */
    public String toShortUrl(UrlRequestDTO request) {
        String shortUrl = Base64.encodeBase64URLSafeString(request.getUrl().getPath().getBytes());
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .longUrl(request.getUrl().getPath())
                .createdAt(LocalDateTime.now())
                .build();
        urlRepository.save(url);
        log.info("Short URL saved. URL: " + shortUrl);
        return shortUrl;
    }

    public URI toLongUrl(String shortUrl) {
        Url url = urlRepository.findById(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Url not found"));
        return URI.create(url.getLongUrl());
    }

    /*public String toShortUrlCustom(UrlRequestDTO request) {
        String shortUrl = urlConversionService.encode(request.getLongUrl());
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .longUrl(request.getLongUrl())
                .createdAt(LocalDateTime.now())
                .build();
        urlRepository.save(url);
        log.info("Short URL saved.");
        return shortUrl;
    }*/


}
