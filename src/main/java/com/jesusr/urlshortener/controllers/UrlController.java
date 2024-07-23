package com.jesusr.urlshortener.controllers;

import com.jesusr.urlshortener.models.Url;
import com.jesusr.urlshortener.models.UrlDto;
import com.jesusr.urlshortener.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@Controller
public class UrlController
{
    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String getHome(Model model)
    {
        model.addAttribute("urlDto", new UrlDto());
        return "index";
    }

    @PostMapping("/")
    public String postUrl(@Valid UrlDto urlDto, BindingResult bindingResult, Model model)
    {
        Url url;
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        if(bindingResult.hasErrors())
        {
            model.addAttribute("urlDto", urlDto);
            model.addAttribute("errorMsg", "Invalid URL");
            return "index";
        }

        if (urlService.existsUrl(urlDto.getLongUrl()))
        {
            url = urlService.getByLongUrl(urlDto.getLongUrl());
        } else
        {
            url = urlService.createUrl(urlDto);
        }

        model.addAttribute("urlObject", url);
        model.addAttribute("currentUri", uri);
        return "index";
    }

    @GetMapping("/mostClicked")
    public String getMostClicked(Model model)
    {
        String rootUri = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(null).build().toUriString();

        List<Url> mostClicked = urlService.getAllOrderByClickCounter();
        model.addAttribute("mostClicked", mostClicked);
        model.addAttribute("rootUri", rootUri);
        return "mostClicked";
    }

    @GetMapping("/{key}")
    public String redirectTo(@PathVariable String key)
    {
        Url url = urlService.getByUrlKey(key);

        if(url == null)
        {
            return "error";
        }

        return "redirect:" + url.getLongUrl();
    }

}
