package com.jesusr.urlshortener.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Entity
@Data
@Table(name="urls")
public class Url
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="long_url", columnDefinition="TEXT")
    @URL
    private String longUrl;

    @Column(name="url_key")
    private String urlKey;

    @Column(name="click_counter")
    private int clickCounter;
}
