package io.github.mikeyfreake.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Trophy.
 */
@Entity
@Table(name = "trophy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trophy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "trophy_name", length = 255, nullable = false)
    private String trophyName;

    @ManyToOne
    @NotNull
    private League league;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrophyName() {
        return trophyName;
    }

    public Trophy trophyName(String trophyName) {
        this.trophyName = trophyName;
        return this;
    }

    public void setTrophyName(String trophyName) {
        this.trophyName = trophyName;
    }

    public League getLeague() {
        return league;
    }

    public Trophy league(League league) {
        this.league = league;
        return this;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trophy trophy = (Trophy) o;
        if(trophy.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, trophy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Trophy{" +
            "id=" + id +
            ", trophyName='" + trophyName + "'" +
            '}';
    }
}
