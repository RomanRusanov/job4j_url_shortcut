package ru.job4j.urlshortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.domain.Code;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.services.StatisticService;
import ru.job4j.urlshortcut.services.UrlService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Roman Rusanov
 * @since 14.04.2021
 * email roman9628@gmail.com
 */
@RestController
public class UrlController {

    private final UrlService urlService;
    private final StatisticService statisticService;

    public UrlController(UrlService urlService, StatisticService statisticService) {
        this.urlService = urlService;
        this.statisticService = statisticService;
    }

    @PostMapping("/convert")
    public ResponseEntity<Code> convert(@RequestBody Url url) {
        return new ResponseEntity<>(
                this.urlService.getCode(url),
                HttpStatus.OK);
    }

    @GetMapping("/redirect/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) {
        Optional<Url> url = this.urlService.findUrlByCode(code);
        if (url.isPresent()) {
            response.setStatus(302);
            response.addHeader("REDIRECT", url.get().getUrl());
            this.statisticService.updateUrlStatistic(url.get());
        }
    }
}