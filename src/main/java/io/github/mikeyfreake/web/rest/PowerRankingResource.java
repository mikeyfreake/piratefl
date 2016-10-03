package io.github.mikeyfreake.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.mikeyfreake.domain.PowerRanking;

import io.github.mikeyfreake.repository.PowerRankingRepository;
import io.github.mikeyfreake.web.rest.util.HeaderUtil;
import io.github.mikeyfreake.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PowerRanking.
 */
@RestController
@RequestMapping("/api")
public class PowerRankingResource {

    private final Logger log = LoggerFactory.getLogger(PowerRankingResource.class);
        
    @Inject
    private PowerRankingRepository powerRankingRepository;

    /**
     * POST  /power-rankings : Create a new powerRanking.
     *
     * @param powerRanking the powerRanking to create
     * @return the ResponseEntity with status 201 (Created) and with body the new powerRanking, or with status 400 (Bad Request) if the powerRanking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/power-rankings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PowerRanking> createPowerRanking(@Valid @RequestBody PowerRanking powerRanking) throws URISyntaxException {
        log.debug("REST request to save PowerRanking : {}", powerRanking);
        if (powerRanking.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("powerRanking", "idexists", "A new powerRanking cannot already have an ID")).body(null);
        }
        PowerRanking result = powerRankingRepository.save(powerRanking);
        return ResponseEntity.created(new URI("/api/power-rankings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("powerRanking", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /power-rankings : Updates an existing powerRanking.
     *
     * @param powerRanking the powerRanking to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated powerRanking,
     * or with status 400 (Bad Request) if the powerRanking is not valid,
     * or with status 500 (Internal Server Error) if the powerRanking couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/power-rankings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PowerRanking> updatePowerRanking(@Valid @RequestBody PowerRanking powerRanking) throws URISyntaxException {
        log.debug("REST request to update PowerRanking : {}", powerRanking);
        if (powerRanking.getId() == null) {
            return createPowerRanking(powerRanking);
        }
        PowerRanking result = powerRankingRepository.save(powerRanking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("powerRanking", powerRanking.getId().toString()))
            .body(result);
    }

    /**
     * GET  /power-rankings : get all the powerRankings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of powerRankings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/power-rankings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PowerRanking>> getAllPowerRankings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PowerRankings");
        Page<PowerRanking> page = powerRankingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/power-rankings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /power-rankings/:id : get the "id" powerRanking.
     *
     * @param id the id of the powerRanking to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the powerRanking, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/power-rankings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PowerRanking> getPowerRanking(@PathVariable Long id) {
        log.debug("REST request to get PowerRanking : {}", id);
        PowerRanking powerRanking = powerRankingRepository.findOne(id);
        return Optional.ofNullable(powerRanking)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /power-rankings/:id : delete the "id" powerRanking.
     *
     * @param id the id of the powerRanking to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/power-rankings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePowerRanking(@PathVariable Long id) {
        log.debug("REST request to delete PowerRanking : {}", id);
        powerRankingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("powerRanking", id.toString())).build();
    }

}
