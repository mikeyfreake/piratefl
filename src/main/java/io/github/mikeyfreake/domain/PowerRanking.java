package io.github.mikeyfreake.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PowerRanking.
 */
@Entity
@Table(name = "power_ranking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PowerRanking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "week", nullable = false)
    private Integer week;

    @NotNull
    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @ManyToOne
    @NotNull
    private Team team;

    @ManyToOne
    @NotNull
    private Season season;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeek() {
        return week;
    }

    public PowerRanking week(Integer week) {
        this.week = week;
        return this;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getRank() {
        return rank;
    }

    public PowerRanking rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getComments() {
        return comments;
    }

    public PowerRanking comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Team getTeam() {
        return team;
    }

    public PowerRanking team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Season getSeason() {
        return season;
    }

    public PowerRanking season(Season season) {
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
        PowerRanking powerRanking = (PowerRanking) o;
        if(powerRanking.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, powerRanking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PowerRanking{" +
            "id=" + id +
            ", week='" + week + "'" +
            ", rank='" + rank + "'" +
            ", comments='" + comments + "'" +
            '}';
    }
}
