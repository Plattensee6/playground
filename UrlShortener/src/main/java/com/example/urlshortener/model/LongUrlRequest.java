package com.example.urlshortener.model;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LongUrlRequest {
    @NotNull
    @NotBlank
    private String longUrl;
}
