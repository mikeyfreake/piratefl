package io.github.mikeyfreake.repository;

import io.github.mikeyfreake.domain.Season;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Season entity.
 */
@SuppressWarnings("unused")
public interface SeasonRepository extends JpaRepository<Season,Long> {

}
