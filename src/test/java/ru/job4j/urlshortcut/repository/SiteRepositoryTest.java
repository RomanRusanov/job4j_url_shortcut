package ru.job4j.urlshortcut.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.domain.Site;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@ActiveProfiles("hsqldb")
class SiteRepositoryTest {

    @Autowired
    private SiteRepository siteRepository;

    @Test
    void whenSitePresentInDbThenReturnTrue() {
        this.siteRepository.save(Site.builder().name("mail.ru").build());
        assertTrue(this.siteRepository.existsSiteByName("mail.ru"));
    }

}