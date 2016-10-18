package io.github.mikeyfreake.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Season.
 */
@Entity
@Table(name = "season")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Season implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @ManyToOne
    @NotNull
    private League league;

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TeamStats> teamStats = new HashSet<>();

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PowerRanking> powerRankings = new HashSet<>();

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TrophyWinner> trophyWinners = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public Season year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public League getLeague() {
        return league;
    }

    public Season league(League league) {
        this.league = league;
        return this;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Set<TeamStats> getTeamStats() {
        return teamStats;
    }

    public Season teamStats(Set<TeamStats> teamStats) {
        this.teamStats = teamStats;
        return this;
    }

    public Season addTeamStats(TeamStats teamStats) {
        this.teamStats.add(teamStats);
        teamStats.setSeason(this);
        return this;
    }

    public Season removeTeamStats(TeamStats teamStats) {
        this.teamStats.remove(teamStats);
        teamStats.setSeason(null);
        return this;
    }

    public void setTeamStats(Set<TeamStats> teamStats) {
        this.teamStats = teamStats;
    }

    public Set<PowerRanking> getPowerRankings() {
        return powerRankings;
    }

    public Season powerRankings(Set<PowerRanking> powerRankings) {
        this.powerRankings = powerRankings;
        return this;
    }

    public Season addPowerRankings(PowerRanking powerRanking) {
        powerRankings.add(powerRanking);
        powerRanking.setSeason(this);
        return this;
    }

    public Season removePowerRankings(PowerRanking powerRanking) {
        powerRankings.remove(powerRanking);
        powerRanking.setSeason(null);
        return this;
    }

    public void setPowerRankings(Set<PowerRanking> powerRankings) {
        this.powerRankings = powerRankings;
    }

    public Set<TrophyWinner> getTrophyWinners() {
        return trophyWinners;
    }

    public Season trophyWinners(Set<TrophyWinner> trophyWinners) {
        this.trophyWinners = trophyWinners;
        return this;
    }

    public Season addTrophyWinners(TrophyWinner trophyWinner) {
        trophyWinners.add(trophyWinner);
        trophyWinner.setSeason(this);
        return this;
    }

    public Season removeTrophyWinners(TrophyWinner trophyWinner) {
        trophyWinners.remove(trophyWinner);
        trophyWinner.setSeason(null);
        return this;
    }

    public void setTrophyWinners(Set<TrophyWinner> trophyWinners) {
        this.trophyWinners = trophyWinners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Season season = (Season) o;
        if(season.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, season.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Season{" +
            "id=" + id +
            ", year='" + year + "'" +
            '}';
    }
}
