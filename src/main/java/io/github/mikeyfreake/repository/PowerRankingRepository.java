package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.PowerRanking;

/**
 * Spring Data JPA repository for the PowerRanking entity.
 */
@SuppressWarnings("unused")
public interface PowerRankingRepository extends JpaRepository<PowerRanking,Long> {

}
