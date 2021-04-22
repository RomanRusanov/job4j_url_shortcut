package ru.job4j.urlshortcut.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.domain.Authority;
@SpringBootTest
@ActiveProfiles("hsqldb")
class StatisticRepositoryTest {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Test
    void callProcedureIncrementColumn() {
        this.authoritiesRepository.save(Authority.builder().authority("ROLE_USER").build());
        Assertions.assertEquals("ROLE_USER", this.authoritiesRepository.getAuthoritiesByAuthority("ROLE_USER").getAuthority());
    }
}