package io.github.mikeyfreake.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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

    @Column(name = "draft_position")
    private Integer draftPosition;

    @Column(name = "finished")
    private Integer finished;

    @ManyToOne
    @NotNull
    private Season season;

    @OneToOne
    @NotNull
    @JoinColumn(unique = true)
    private Team team;

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

    public Integer getDraftPosition() {
        return draftPosition;
    }

    public TeamStats draftPosition(Integer draftPosition) {
        this.draftPosition = draftPosition;
        return this;
    }

    public void setDraftPosition(Integer draftPosition) {
        this.draftPosition = draftPosition;
    }

    public Integer getFinished() {
        return finished;
    }

    public TeamStats finished(Integer finished) {
        this.finished = finished;
        return this;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
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

    public Team getTeam() {
        return team;
    }

    public TeamStats team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
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
            ", draftPosition='" + draftPosition + "'" +
            ", finished='" + finished + "'" +
            '}';
    }
}
