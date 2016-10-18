package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.TeamStats;

/**
 * Spring Data JPA repository for the TeamStats entity.
 */
@SuppressWarnings("unused")
public interface TeamStatsRepository extends JpaRepository<TeamStats,Long> {

}
