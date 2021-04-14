package ru.job4j.urlshortcut.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.StatisticResponse;
import ru.job4j.urlshortcut.domain.User;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Rusanov
 * @since 14.04.2021
 * email roman9628@gmail.com
 */
@Log4j2
@RestController
public class StatisticController {

    private final SiteRepository siteRepository;
    private final UserService userService;

    public StatisticController(SiteRepository siteRepository, UserService userService) {
        this.siteRepository = siteRepository;
        this.userService = userService;
    }

    @GetMapping("/statistic")
    public List<StatisticResponse> statistic(@AuthenticationPrincipal String username) {
        User user = this.userService.getUserByUsername(username);
        Site site = this.siteRepository.getSiteByUser(user);
        ArrayList<StatisticResponse> convertedStats = new ArrayList<>();
        site.getStatistics().stream().forEach(statistic -> {
            StatisticResponse statisticResponse = StatisticResponse.builder()
                    .url(statistic.getUrl().getUrl())
                    .total(statistic.getTotalCalls())
                    .build();
            convertedStats.add(statisticResponse);
        });
        return convertedStats;
    }
}