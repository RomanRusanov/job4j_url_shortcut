package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Authority;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
public interface AuthoritiesRepository extends CrudRepository<Authority, Long> {
    Authority getAuthoritiesByAuthority(String authority);
}