package ru.job4j.urlshortcut.services;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.User;
import ru.job4j.urlshortcut.repository.UserRepository;

import java.util.UUID;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    public boolean isUserExist(String username) {
        return this.userRepository.existsUserByUsername(username);
    }

    public String getNewLogin() {
        boolean exist = true;
        String login = "";
        while (exist) {
            login = this.getUniqueString();
            exist = this.isUserExist(login);
        }
        return login;
    }

    public String getUniqueString() {
        return UUID.randomUUID().toString();
    }
}