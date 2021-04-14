package ru.job4j.urlshortcut.services;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Authority;
import ru.job4j.urlshortcut.repository.AuthoritiesRepository;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
@Service
public class AuthorityService {

    private final AuthoritiesRepository authoritiesRepository;


    public AuthorityService(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    public Authority getAuthorityByAuthority(String name) {
        return this.authoritiesRepository.getAuthoritiesByAuthority(name);
    }
}