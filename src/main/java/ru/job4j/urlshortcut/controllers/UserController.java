package ru.job4j.urlshortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.domain.jsonmodels.Registration;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.jsonmodels.SiteName;
import ru.job4j.urlshortcut.domain.User;
import ru.job4j.urlshortcut.services.AuthorityService;
import ru.job4j.urlshortcut.services.SiteService;
import ru.job4j.urlshortcut.services.UserService;

import javax.transaction.Transactional;

@RestController
public class UserController {

    private final UserService userService;
    private final SiteService siteService;
    private final AuthorityService authorityService;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService users,
                          SiteService siteService,
                          AuthorityService authorityService,
                          BCryptPasswordEncoder encoder) {
        this.userService = users;
        this.siteService = siteService;
        this.authorityService = authorityService;
        this.encoder = encoder;
    }

    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<Registration> registration(@RequestBody SiteName siteName) {
        Site site = Site.builder()
                .name(siteName.getSite())
                .build();
        if (this.siteService.isSiteRegistered(site.getName())) {
            return new ResponseEntity<>(
                    new Registration(false, "", ""),
                    HttpStatus.CONFLICT);
        }
        String username = this.userService.getNewLogin();
        String password = this.userService.getUniqueString();
        Long authorityId = this.authorityService.getAuthorityByAuthority("ROLE_USER").getId();
        User user = User.builder()
                .site(site)
                .username(username)
                .password(encoder.encode(password))
                .authorityId(authorityId)
                .build();
        site.assignUser(user);
        this.userService.save(user);
        this.siteService.save(site);
        return new ResponseEntity<>(
                new Registration(true, username, password),
                HttpStatus.OK);
    }

    @GetMapping("/onlyAuthenticated")
    public ResponseEntity<Void> checkAuthentication() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}