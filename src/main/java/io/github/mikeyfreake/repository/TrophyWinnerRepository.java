package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.TrophyWinner;

/**
 * Spring Data JPA repository for the TrophyWinner entity.
 */
@SuppressWarnings("unused")
public interface TrophyWinnerRepository extends JpaRepository<TrophyWinner,Long> {

}
