package io.github.mikeyfreake.repository;

import io.github.mikeyfreake.domain.PowerRanking;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PowerRanking entity.
 */
@SuppressWarnings("unused")
public interface PowerRankingRepository extends JpaRepository<PowerRanking,Long> {

}
