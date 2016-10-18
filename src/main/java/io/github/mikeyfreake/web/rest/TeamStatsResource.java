package io.github.mikeyfreake.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.mikeyfreake.domain.TeamStats;
import io.github.mikeyfreake.repository.TeamStatsRepository;
import io.github.mikeyfreake.web.rest.util.HeaderUtil;
import io.github.mikeyfreake.web.rest.util.PaginationUtil;

/**
 * REST controller for managing TeamStats.
 */
@RestController
@RequestMapping("/api")
public class TeamStatsResource {

    private final Logger log = LoggerFactory.getLogger(TeamStatsResource.class);
        
    @Inject
    private TeamStatsRepository teamStatsRepository;

    /**
     * POST  /team-stats : Create a new teamStats.
     *
     * @param teamStats the teamStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamStats, or with status 400 (Bad Request) if the teamStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/team-stats",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TeamStats> createTeamStats(@Valid @RequestBody TeamStats teamStats) throws URISyntaxException {
        log.debug("REST request to save TeamStats : {}", teamStats);
        if (teamStats.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("teamStats", "idexists", "A new teamStats cannot already have an ID")).body(null);
        }
        TeamStats result = teamStatsRepository.save(teamStats);
        return ResponseEntity.created(new URI("/api/team-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("teamStats", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-stats : Updates an existing teamStats.
     *
     * @param teamStats the teamStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamStats,
     * or with status 400 (Bad Request) if the teamStats is not valid,
     * or with status 500 (Internal Server Error) if the teamStats couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/team-stats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TeamStats> updateTeamStats(@Valid @RequestBody TeamStats teamStats) throws URISyntaxException {
        log.debug("REST request to update TeamStats : {}", teamStats);
        if (teamStats.getId() == null) {
            return createTeamStats(teamStats);
        }
        TeamStats result = teamStatsRepository.save(teamStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("teamStats", teamStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-stats : get all the teamStats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teamStats in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/team-stats",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TeamStats>> getAllTeamStats(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TeamStats");
        Page<TeamStats> page = teamStatsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/team-stats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /team-stats/:id : get the "id" teamStats.
     *
     * @param id the id of the teamStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamStats, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/team-stats/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TeamStats> getTeamStats(@PathVariable Long id) {
        log.debug("REST request to get TeamStats : {}", id);
        TeamStats teamStats = teamStatsRepository.findOne(id);
        return Optional.ofNullable(teamStats)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /team-stats/:id : delete the "id" teamStats.
     *
     * @param id the id of the teamStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/team-stats/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTeamStats(@PathVariable Long id) {
        log.debug("REST request to delete TeamStats : {}", id);
        teamStatsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("teamStats", id.toString())).build();
    }

}
