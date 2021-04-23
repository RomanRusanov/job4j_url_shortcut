package ru.job4j.urlshortcut.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.domain.Authority;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.domain.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@ActiveProfiles("hsqldb")
class SiteRepositoryTest {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlRepository urlRepository;

    @Test
    void whenSitePresentInDbThenReturnTrue() {
        this.siteRepository.save(Site.builder().name("mail.ru").build());
        assertTrue(this.siteRepository.existsSiteByName("mail.ru"));
    }

//    @Test
//    void getSiteByUser() {
//        User user = User.builder().username("user").password("password").build();
//        user = this.userRepository.save(user);
//        Url url = Url.builder().url("mail.ru/someUrl")
//                .code("qWdEf5g")
//                .build();
//        url = this.urlRepository.save(url);
//        Site site = this.siteRepository.save(Site.builder()
//                .name("mail.ru")
//                .user(user)
//                .urls(Set.of())
//                .statistics(Set.of())
//                .build());
//        site.addUrl(url);
//        site = this.siteRepository.save(site);
//        user.setSite(site);
//        user = this.userRepository.save(user);
//        Statistic statistic = Statistic.builder().url(url).site(site).build();
//        site.addStatistic(statistic);
//        site = this.siteRepository.save(site);
//        assertEquals(site.getId(), this.siteRepository.getSiteByUser(user).getId());
//    }
}