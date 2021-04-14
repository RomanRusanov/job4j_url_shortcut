package ru.job4j.urlshortcut.security;

import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.services.UserService;

import static java.util.Collections.emptyList;
@Log
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService users;

    public UserDetailsServiceImpl(UserService users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.job4j.urlshortcut.domain.User user = users.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        log.info("Successful login user: " + user.getUsername());
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}