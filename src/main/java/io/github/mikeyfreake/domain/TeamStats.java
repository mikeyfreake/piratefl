package io.github.mikeyfreake.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TeamStats.
 */
@Entity
@Table(name = "team_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "ties")
    private Integer ties;

    @Column(name = "points_for")
    private Integer pointsFor;

    @Column(name = "points_against")
    private Integer pointsAgainst;

    @ManyToOne
    @NotNull
    private Season season;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWins() {
        return wins;
    }

    public TeamStats wins(Integer wins) {
        this.wins = wins;
        return this;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public TeamStats losses(Integer losses) {
        this.losses = losses;
        return this;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }

    public TeamStats ties(Integer ties) {
        this.ties = ties;
        return this;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    public Integer getPointsFor() {
        return pointsFor;
    }

    public TeamStats pointsFor(Integer pointsFor) {
        this.pointsFor = pointsFor;
        return this;
    }

    public void setPointsFor(Integer pointsFor) {
        this.pointsFor = pointsFor;
    }

    public Integer getPointsAgainst() {
        return pointsAgainst;
    }

    public TeamStats pointsAgainst(Integer pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
        return this;
    }

    public void setPointsAgainst(Integer pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    public Season getSeason() {
        return season;
    }

    public TeamStats season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeamStats teamStats = (TeamStats) o;
        if(teamStats.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, teamStats.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TeamStats{" +
            "id=" + id +
            ", wins='" + wins + "'" +
            ", losses='" + losses + "'" +
            ", ties='" + ties + "'" +
            ", pointsFor='" + pointsFor + "'" +
            ", pointsAgainst='" + pointsAgainst + "'" +
            '}';
    }
}
