package io.github.mikeyfreake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mikeyfreake.domain.Team;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
public interface TeamRepository extends JpaRepository<Team,Long> {

}
