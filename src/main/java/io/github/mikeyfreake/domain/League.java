package io.github.mikeyfreake.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A League.
 */
@Entity
@Table(name = "league")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "league_name", length = 128, nullable = false)
    private String leagueName;

    @Size(max = 25000)
    @Column(name = "constitution", length = 25000)
    private String constitution;

    @OneToMany(mappedBy = "league")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Season> seasons = new HashSet<>();

    @OneToMany(mappedBy = "league")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "league")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trophy> trophies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public League leagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getConstitution() {
        return constitution;
    }

    public League constitution(String constitution) {
        this.constitution = constitution;
        return this;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public League seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public League addSeasons(Season season) {
        seasons.add(season);
        season.setLeague(this);
        return this;
    }

    public League removeSeasons(Season season) {
        seasons.remove(season);
        season.setLeague(null);
        return this;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public League teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public League addTeams(Team team) {
        teams.add(team);
        team.setLeague(this);
        return this;
    }

    public League removeTeams(Team team) {
        teams.remove(team);
        team.setLeague(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Trophy> getTrophies() {
        return trophies;
    }

    public League trophies(Set<Trophy> trophies) {
        this.trophies = trophies;
        return this;
    }

    public League addTrophies(Trophy trophy) {
        trophies.add(trophy);
        trophy.setLeague(this);
        return this;
    }

    public League removeTrophies(Trophy trophy) {
        trophies.remove(trophy);
        trophy.setLeague(null);
        return this;
    }

    public void setTrophies(Set<Trophy> trophies) {
        this.trophies = trophies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        League league = (League) o;
        if(league.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, league.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "League{" +
            "id=" + id +
            ", leagueName='" + leagueName + "'" +
            ", constitution='" + constitution + "'" +
            '}';
    }
}
