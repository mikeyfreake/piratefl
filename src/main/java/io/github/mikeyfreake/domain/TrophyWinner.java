package io.github.mikeyfreake.domain;

import java.io.Serializable;
import java.util.Objects;

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
 * A TrophyWinner.
 */
@Entity
@Table(name = "trophy_winner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrophyWinner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Season season;

    @OneToOne
    @NotNull
    @JoinColumn(unique = true)
    private Team team;

    @OneToOne
    @NotNull
    @JoinColumn(unique = true)
    private Trophy trophy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Season getSeason() {
        return season;
    }

    public TrophyWinner season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Team getTeam() {
        return team;
    }

    public TrophyWinner team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Trophy getTrophy() {
        return trophy;
    }

    public TrophyWinner trophy(Trophy trophy) {
        this.trophy = trophy;
        return this;
    }

    public void setTrophy(Trophy trophy) {
        this.trophy = trophy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrophyWinner trophyWinner = (TrophyWinner) o;
        if(trophyWinner.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, trophyWinner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrophyWinner{" +
            "id=" + id +
            '}';
    }
}
