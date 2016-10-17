package io.github.mikeyfreake.repository;

import io.github.mikeyfreake.domain.League;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the League entity.
 */
@SuppressWarnings("unused")
public interface LeagueRepository extends JpaRepository<League,Long> {

}
