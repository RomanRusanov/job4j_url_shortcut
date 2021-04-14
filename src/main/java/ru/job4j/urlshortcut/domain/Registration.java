package ru.job4j.urlshortcut.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 * {registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}
 */
@Getter
@ToString
@AllArgsConstructor
public class Registration {
    private final boolean registration;
    private final String login;
    private final String password;
}