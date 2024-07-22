package com.jesusr.urlshortener.models;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UrlDto
{
    @URL
    private String longUrl;
}
