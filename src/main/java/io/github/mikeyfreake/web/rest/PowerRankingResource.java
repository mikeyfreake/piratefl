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

import io.github.mikeyfreake.service.PowerRankingService;
import io.github.mikeyfreake.service.dto.PowerRankingDTO;
import io.github.mikeyfreake.web.rest.util.HeaderUtil;
import io.github.mikeyfreake.web.rest.util.PaginationUtil;

/**
 * REST controller for managing PowerRanking.
 */
@RestController
@RequestMapping("/api")
public class PowerRankingResource {

    private final Logger log = LoggerFactory.getLogger(PowerRankingResource.class);
        
    @Inject
    private PowerRankingService powerRankingService;

    /**
     * POST  /power-rankings : Create a new powerRanking.
     *
     * @param powerRankingDTO the powerRankingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new powerRankingDTO, or with status 400 (Bad Request) if the powerRanking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/power-rankings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PowerRankingDTO> createPowerRanking(@Valid @RequestBody PowerRankingDTO powerRankingDTO) throws URISyntaxException {
        log.debug("REST request to save PowerRanking : {}", powerRankingDTO);
        if (powerRankingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("powerRanking", "idexists", "A new powerRanking cannot already have an ID")).body(null);
        }
        PowerRankingDTO result = powerRankingService.save(powerRankingDTO);
        return ResponseEntity.created(new URI("/api/power-rankings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("powerRanking", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /power-rankings : Updates an existing powerRanking.
     *
     * @param powerRankingDTO the powerRankingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated powerRankingDTO,
     * or with status 400 (Bad Request) if the powerRankingDTO is not valid,
     * or with status 500 (Internal Server Error) if the powerRankingDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/power-rankings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PowerRankingDTO> updatePowerRanking(@Valid @RequestBody PowerRankingDTO powerRankingDTO) throws URISyntaxException {
        log.debug("REST request to update PowerRanking : {}", powerRankingDTO);
        if (powerRankingDTO.getId() == null) {
            return createPowerRanking(powerRankingDTO);
        }
        PowerRankingDTO result = powerRankingService.save(powerRankingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("powerRanking", powerRankingDTO.getId().toString()))
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
    public ResponseEntity<List<PowerRankingDTO>> getAllPowerRankings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PowerRankings");
        Page<PowerRankingDTO> page = powerRankingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/power-rankings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /power-rankings/:id : get the "id" powerRanking.
     *
     * @param id the id of the powerRankingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the powerRankingDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/power-rankings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PowerRankingDTO> getPowerRanking(@PathVariable Long id) {
        log.debug("REST request to get PowerRanking : {}", id);
        PowerRankingDTO powerRankingDTO = powerRankingService.findOne(id);
        return Optional.ofNullable(powerRankingDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /power-rankings/:id : delete the "id" powerRanking.
     *
     * @param id the id of the powerRankingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/power-rankings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePowerRanking(@PathVariable Long id) {
        log.debug("REST request to delete PowerRanking : {}", id);
        powerRankingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("powerRanking", id.toString())).build();
    }

}
