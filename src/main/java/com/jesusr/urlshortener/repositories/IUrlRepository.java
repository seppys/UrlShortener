package com.jesusr.urlshortener.repositories;

import com.jesusr.urlshortener.models.Url;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface IUrlRepository extends CrudRepository<Url, Long>
{
    Url findById(long id);
    List<Url> findAll();
    Url findByLongUrl(String longURl);
    Url findByUrlKey(String urlKey);
    boolean existsByLongUrl(String longUrl);
    boolean existsByUrlKey(String urlKey);

}
