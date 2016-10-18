package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.Season;

/**
 * Spring Data JPA repository for the Season entity.
 */
@SuppressWarnings("unused")
public interface SeasonRepository extends JpaRepository<Season,Long> {

}
