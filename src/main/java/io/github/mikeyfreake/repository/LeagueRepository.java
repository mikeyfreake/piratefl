package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.League;

/**
 * Spring Data JPA repository for the League entity.
 */
@SuppressWarnings("unused")
public interface LeagueRepository extends JpaRepository<League,Long> {

}
