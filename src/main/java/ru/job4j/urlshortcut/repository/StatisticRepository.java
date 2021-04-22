package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Statistic;

/**
 * @author Roman Rusanov
 * @since 14.04.2021
 * email roman9628@gmail.com
 */
public interface StatisticRepository extends CrudRepository<Statistic, Long> {

    @Query(value = "CALL updatestatistic(:urlId);", nativeQuery = true)
    void callProcedureIncrementColumn(Long urlId);
}