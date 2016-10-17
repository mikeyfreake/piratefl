package io.github.mikeyfreake.repository;

import io.github.mikeyfreake.domain.Trophy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Trophy entity.
 */
@SuppressWarnings("unused")
public interface TrophyRepository extends JpaRepository<Trophy,Long> {

}
