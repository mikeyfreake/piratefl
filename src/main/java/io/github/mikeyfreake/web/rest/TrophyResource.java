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

import io.github.mikeyfreake.domain.Trophy;
import io.github.mikeyfreake.repository.TrophyRepository;
import io.github.mikeyfreake.web.rest.util.HeaderUtil;
import io.github.mikeyfreake.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Trophy.
 */
@RestController
@RequestMapping("/api")
public class TrophyResource {

    private final Logger log = LoggerFactory.getLogger(TrophyResource.class);
        
    @Inject
    private TrophyRepository trophyRepository;

    /**
     * POST  /trophies : Create a new trophy.
     *
     * @param trophy the trophy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trophy, or with status 400 (Bad Request) if the trophy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/trophies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trophy> createTrophy(@Valid @RequestBody Trophy trophy) throws URISyntaxException {
        log.debug("REST request to save Trophy : {}", trophy);
        if (trophy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("trophy", "idexists", "A new trophy cannot already have an ID")).body(null);
        }
        Trophy result = trophyRepository.save(trophy);
        return ResponseEntity.created(new URI("/api/trophies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trophy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trophies : Updates an existing trophy.
     *
     * @param trophy the trophy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trophy,
     * or with status 400 (Bad Request) if the trophy is not valid,
     * or with status 500 (Internal Server Error) if the trophy couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/trophies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trophy> updateTrophy(@Valid @RequestBody Trophy trophy) throws URISyntaxException {
        log.debug("REST request to update Trophy : {}", trophy);
        if (trophy.getId() == null) {
            return createTrophy(trophy);
        }
        Trophy result = trophyRepository.save(trophy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trophy", trophy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trophies : get all the trophies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trophies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/trophies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Trophy>> getAllTrophies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Trophies");
        Page<Trophy> page = trophyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trophies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trophies/:id : get the "id" trophy.
     *
     * @param id the id of the trophy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trophy, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/trophies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trophy> getTrophy(@PathVariable Long id) {
        log.debug("REST request to get Trophy : {}", id);
        Trophy trophy = trophyRepository.findOne(id);
        return Optional.ofNullable(trophy)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trophies/:id : delete the "id" trophy.
     *
     * @param id the id of the trophy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/trophies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrophy(@PathVariable Long id) {
        log.debug("REST request to delete Trophy : {}", id);
        trophyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trophy", id.toString())).build();
    }

}
