package com.jesusr.urlshortener.services;

import com.jesusr.urlshortener.models.Url;
import com.jesusr.urlshortener.models.UrlDto;

import java.util.List;

public interface IUrlService
{
    List<Url> getAll();

    Url getById(long id);

    Url createUrl(UrlDto urlDto);

    Boolean existsKey(String key);

    Boolean existsUrl(String url);
}
