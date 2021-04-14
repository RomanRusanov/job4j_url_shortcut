package ru.job4j.urlshortcut.services;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.User;
import ru.job4j.urlshortcut.repository.SiteRepository;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
@Service
public class SiteService {

    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public boolean isSiteRegistered(String name) {
        return this.siteRepository.existsSiteByName(name);
    }

    public Site save(Site site) {
        return this.siteRepository.save(site);
    }

    public Site getSiteByUser(User user) {
        return this.siteRepository.getSiteByUser(user);
    }
}