package com.example.urlshortener.model;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.net.URL;

@Getter
public class UrlRequestDTO {
    @NotNull
    private URL url;
}
