package ru.job4j.urlshortcut.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.jsonmodels.Code;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.domain.User;
import ru.job4j.urlshortcut.repository.StatisticRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;


/**
 * @author Roman Rusanov
 * @since 14.04.2021
 * email roman9628@gmail.com
 */
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final SiteService siteService;
    private final UserService userService;
    private final StatisticRepository statisticRepository;

    public UrlService(UrlRepository urlRepository, SiteService siteService, UserService userService, StatisticRepository statisticRepository) {
        this.urlRepository = urlRepository;
        this.siteService = siteService;
        this.userService = userService;
        this.statisticRepository = statisticRepository;
    }

    public Optional<Url> findUrl(String url) {
        return this.urlRepository.getUrlByUrl(url);
    }

    public Optional<Url> findUrlByCode(String code) {
        return this.urlRepository.getUrlByCode(code);
    }

    public Code getCode(Url url) {
        String code;
        Optional<Url> urlFromDB = this.findUrl(url.getUrl());
        if (urlFromDB.isPresent()) {
            code = urlFromDB.get().getCode();
        } else {
            code = this.generateNewCode();
            url.setCode(code);
            addUrlToSite(url);
        }
        return new Code(code);
    }

    private String generateNewCode() {
        boolean exist = true;
        String randomString = "";
        while (exist) {
            randomString = RandomStringUtils.randomAlphabetic(7, 7);
            exist = this.findUrlByCode(randomString).isPresent();
        }
        return randomString;
    }

    private void addUrlToSite(Url url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.getUserByUsername((String) authentication.getPrincipal());
        Site site = this.siteService.getSiteByUser(user);
        Url savedUrl = this.urlRepository.save(url);
        Statistic statistic = Statistic.builder()
                .site(site)
                .url(savedUrl)
                .totalCalls(0L)
                .build();
        this.statisticRepository.save(statistic);
        site.addStatistic(statistic);
        site.addUrl(savedUrl);
        this.siteService.save(site);
    }
}