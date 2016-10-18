package io.github.mikeyfreake.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO for the PowerRanking entity.
 */
public class PowerRankingDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer week;

    @NotNull
    private Integer rank;

    @Size(max = 500)
    private String comments;


    private Long teamId;
    

    private String teamTeamName;

    private Long seasonId;
    

    private String seasonYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }


    public String getTeamTeamName() {
        return teamTeamName;
    }

    public void setTeamTeamName(String teamTeamName) {
        this.teamTeamName = teamTeamName;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }


    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PowerRankingDTO powerRankingDTO = (PowerRankingDTO) o;

        if ( ! Objects.equals(id, powerRankingDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PowerRankingDTO{" +
            "id=" + id +
            ", week='" + week + "'" +
            ", rank='" + rank + "'" +
            ", comments='" + comments + "'" +
            '}';
    }
}
