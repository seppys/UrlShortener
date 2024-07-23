package com.jesusr.urlshortener.services;

import com.jesusr.urlshortener.models.Url;
import com.jesusr.urlshortener.models.UrlDto;
import com.jesusr.urlshortener.repositories.IUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UrlService implements IUrlService
{
    @Autowired
    private IUrlRepository urlRepository;

    public List<Url> getAll()
    {
        return urlRepository.findAll();
    }

    public Url getById(long id)
    {
        return urlRepository.findById(id);
    }

    public List<Url> getAllOrderByClickCounter()
    {
        List<Url> urls = urlRepository.findAll();

        urls = urls.stream()
                .sorted(Comparator.comparingInt(Url::getClickCounter).reversed())
                .toList();

        return urls;
    }

    public Url getByLongUrl(String longUrl)
    {
        return urlRepository.findByLongUrl(longUrl);
    }

    public Url getByUrlKey(String urlKey)
    {
        if(existsKey(urlKey))
        {
            Url url = urlRepository.findByUrlKey(urlKey);
            url.setClickCounter(url.getClickCounter() + 1);
            urlRepository.save(url);
            return url;
        }
        return null;
    }

    public Url createUrl(UrlDto urlDto)
    {
        String key = generateKey();
        String longUrl = urlDto.getLongUrl();

        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);
        newUrl.setUrlKey(key);
        newUrl.setClickCounter(0);

        urlRepository.save(newUrl);
        return newUrl;
    }


    public Boolean existsUrl(String longUrl)
    {
        return urlRepository.existsByLongUrl(longUrl);
    }

    public Boolean existsKey(String urlKey)
    {
        return urlRepository.existsByUrlKey(urlKey);
    }

    public String generateKey()
    {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder stringBuilder;
        String key;

        do
        {
            stringBuilder = new StringBuilder(5);

            for (int i = 0; i < 5; i++)
            {
                stringBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
            }

            key = stringBuilder.toString();
        } while (existsKey(key));

        return key;
    }

}
