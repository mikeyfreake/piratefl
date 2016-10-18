package io.github.mikeyfreake.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.mikeyfreake.domain.PowerRanking;
import io.github.mikeyfreake.domain.Season;
import io.github.mikeyfreake.domain.Team;
import io.github.mikeyfreake.service.dto.PowerRankingDTO;

/**
 * Mapper for the entity PowerRanking and its DTO PowerRankingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PowerRankingMapper {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.teamName", target = "teamTeamName")
    @Mapping(source = "season.id", target = "seasonId")
    @Mapping(source = "season.year", target = "seasonYear")
    PowerRankingDTO powerRankingToPowerRankingDTO(PowerRanking powerRanking);

    List<PowerRankingDTO> powerRankingsToPowerRankingDTOs(List<PowerRanking> powerRankings);

    @Mapping(source = "teamId", target = "team")
    @Mapping(source = "seasonId", target = "season")
    PowerRanking powerRankingDTOToPowerRanking(PowerRankingDTO powerRankingDTO);

    List<PowerRanking> powerRankingDTOsToPowerRankings(List<PowerRankingDTO> powerRankingDTOs);

    default Team teamFromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }

    default Season seasonFromId(Long id) {
        if (id == null) {
            return null;
        }
        Season season = new Season();
        season.setId(id);
        return season;
    }
}
