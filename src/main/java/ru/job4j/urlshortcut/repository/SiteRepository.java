package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.User;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
public interface SiteRepository extends CrudRepository<Site, Long> {

    boolean existsSiteByName(String name);

    Site getSiteByUser(User user);
}