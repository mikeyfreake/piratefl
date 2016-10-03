package io.github.mikeyfreake.repository;

import io.github.mikeyfreake.domain.TrophyWinner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TrophyWinner entity.
 */
@SuppressWarnings("unused")
public interface TrophyWinnerRepository extends JpaRepository<TrophyWinner,Long> {

}
