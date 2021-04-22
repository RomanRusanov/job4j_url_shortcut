package ru.job4j.urlshortcut.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Roman Rusanov
 * @since 05.04.2021
 * email roman9628@gmail.com
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(orphanRemoval = true, mappedBy = "site")
    private User user;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "site_url",
            joinColumns = {@JoinColumn(name = "site_id")},
            inverseJoinColumns = {@JoinColumn(name = "url_id")})
    private Set<Url> urls  = new HashSet<>();
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "site_statistics",
            joinColumns = {@JoinColumn(name = "site_id")},
            inverseJoinColumns = {@JoinColumn(name = "statistics_id")})
    private Set<Statistic> statistics = new HashSet<>();

    public void addUser(User user) {
        this.user = user;
    }

    public void addUrl(Url url) {
        this.urls.add(url);
    }

    public void addStatistic(Statistic statistic) {
        this.statistics.add(statistic);
        statistic.setSite(this);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return Objects.equals(id, site.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}