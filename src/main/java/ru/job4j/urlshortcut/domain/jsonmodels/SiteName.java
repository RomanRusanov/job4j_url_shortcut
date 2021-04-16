package ru.job4j.urlshortcut.domain.jsonmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Roman Rusanov
 * @since 12.04.2021
 * email roman9628@gmail.com
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteName {
    private String site;
}