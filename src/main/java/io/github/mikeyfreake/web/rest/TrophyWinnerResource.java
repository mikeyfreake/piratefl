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

import io.github.mikeyfreake.domain.TrophyWinner;
import io.github.mikeyfreake.repository.TrophyWinnerRepository;
import io.github.mikeyfreake.web.rest.util.HeaderUtil;
import io.github.mikeyfreake.web.rest.util.PaginationUtil;

/**
 * REST controller for managing TrophyWinner.
 */
@RestController
@RequestMapping("/api")
public class TrophyWinnerResource {

    private final Logger log = LoggerFactory.getLogger(TrophyWinnerResource.class);
        
    @Inject
    private TrophyWinnerRepository trophyWinnerRepository;

    /**
     * POST  /trophy-winners : Create a new trophyWinner.
     *
     * @param trophyWinner the trophyWinner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trophyWinner, or with status 400 (Bad Request) if the trophyWinner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/trophy-winners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrophyWinner> createTrophyWinner(@Valid @RequestBody TrophyWinner trophyWinner) throws URISyntaxException {
        log.debug("REST request to save TrophyWinner : {}", trophyWinner);
        if (trophyWinner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("trophyWinner", "idexists", "A new trophyWinner cannot already have an ID")).body(null);
        }
        TrophyWinner result = trophyWinnerRepository.save(trophyWinner);
        return ResponseEntity.created(new URI("/api/trophy-winners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trophyWinner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trophy-winners : Updates an existing trophyWinner.
     *
     * @param trophyWinner the trophyWinner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trophyWinner,
     * or with status 400 (Bad Request) if the trophyWinner is not valid,
     * or with status 500 (Internal Server Error) if the trophyWinner couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/trophy-winners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrophyWinner> updateTrophyWinner(@Valid @RequestBody TrophyWinner trophyWinner) throws URISyntaxException {
        log.debug("REST request to update TrophyWinner : {}", trophyWinner);
        if (trophyWinner.getId() == null) {
            return createTrophyWinner(trophyWinner);
        }
        TrophyWinner result = trophyWinnerRepository.save(trophyWinner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trophyWinner", trophyWinner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trophy-winners : get all the trophyWinners.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trophyWinners in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/trophy-winners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrophyWinner>> getAllTrophyWinners(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TrophyWinners");
        Page<TrophyWinner> page = trophyWinnerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trophy-winners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trophy-winners/:id : get the "id" trophyWinner.
     *
     * @param id the id of the trophyWinner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trophyWinner, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/trophy-winners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrophyWinner> getTrophyWinner(@PathVariable Long id) {
        log.debug("REST request to get TrophyWinner : {}", id);
        TrophyWinner trophyWinner = trophyWinnerRepository.findOne(id);
        return Optional.ofNullable(trophyWinner)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trophy-winners/:id : delete the "id" trophyWinner.
     *
     * @param id the id of the trophyWinner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/trophy-winners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrophyWinner(@PathVariable Long id) {
        log.debug("REST request to delete TrophyWinner : {}", id);
        trophyWinnerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trophyWinner", id.toString())).build();
    }

}
