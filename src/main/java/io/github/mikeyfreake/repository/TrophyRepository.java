package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.Trophy;

/**
 * Spring Data JPA repository for the Trophy entity.
 */
@SuppressWarnings("unused")
public interface TrophyRepository extends JpaRepository<Trophy,Long> {

}
