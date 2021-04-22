package ru.job4j.urlshortcut.services;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.repository.StatisticRepository;

import javax.transaction.Transactional;

/**
 * @author Roman Rusanov
 * @since 14.04.2021
 * email roman9628@gmail.com
 */
@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Transactional
    public void updateUrlStatistic(Url url) {
        this.statisticRepository.callProcedureIncrementColumn(url.getId());
    }
}