package io.github.mikeyfreake.repository;

import io.github.mikeyfreake.domain.TeamStats;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TeamStats entity.
 */
@SuppressWarnings("unused")
public interface TeamStatsRepository extends JpaRepository<TeamStats,Long> {

}
