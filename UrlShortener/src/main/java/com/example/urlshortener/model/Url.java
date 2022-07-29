package com.example.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.net.URL;
import java.time.LocalDateTime;

@Entity
@Table(name = "url")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @Column(name = "short_url")
    private String shortUrl;
    @Column(name = "long_url")
    private String longUrl;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
