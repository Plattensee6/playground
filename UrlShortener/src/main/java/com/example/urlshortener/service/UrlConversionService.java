package com.example.urlshortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlConversionService {
    @Value("${converter.allowed-characters}")
    private String allowedCharacterString;

    public String encode(String url) {
        if(url == null || url.length() == 0){
            throw new IllegalArgumentException("Invalid url.");
        }
        int base = allowedCharacterString.length();
        char[] urlCharacters = url.toCharArray();
        var encodedUrl = new StringBuilder();
        for(char ch : urlCharacters){
            StringBuilder encodedChar = new StringBuilder();
            int characterNumber = ch;
            while (characterNumber > 0) {
                int index = characterNumber % base;
                encodedChar.append(allowedCharacterString.charAt(index));
                characterNumber = characterNumber / base;
            }
            encodedUrl.append(encodedChar);
        }

        return encodedUrl.reverse().toString();
    }
    public long decode(String encodedUrl){
        var characters = encodedUrl.toCharArray();
        var length = characters.length;
        var decoded = 0;
        var counter = 1;
        for(int i = 0; i < characters.length; i++){
            decoded += allowedCharacterString.indexOf(characters[i]) * Math.pow(allowedCharacterString.length(), length - counter);
            counter++;
        }
        return decoded;
    }
}
