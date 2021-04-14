package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.User;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);
}