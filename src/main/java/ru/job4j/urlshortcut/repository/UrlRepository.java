package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Url;

import java.util.Optional;

/**
 * @author Roman Rusanov
 * @since 14.04.2021
 * email roman9628@gmail.com
 */
public interface UrlRepository extends CrudRepository<Url, Long> {

    Optional<Url> getUrlByUrl(String url);

    Optional<Url> getUrlByCode(String code);
}