package io.github.mikeyfreake.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mikeyfreake.domain.PowerRanking;
import io.github.mikeyfreake.repository.PowerRankingRepository;
import io.github.mikeyfreake.service.dto.PowerRankingDTO;
import io.github.mikeyfreake.service.mapper.PowerRankingMapper;

/**
 * Service Implementation for managing PowerRanking.
 */
@Service
@Transactional
public class PowerRankingService {

    private final Logger log = LoggerFactory.getLogger(PowerRankingService.class);
    
    @Inject
    private PowerRankingRepository powerRankingRepository;

    @Inject
    private PowerRankingMapper powerRankingMapper;

    /**
     * Save a powerRanking.
     *
     * @param powerRankingDTO the entity to save
     * @return the persisted entity
     */
    public PowerRankingDTO save(PowerRankingDTO powerRankingDTO) {
        log.debug("Request to save PowerRanking : {}", powerRankingDTO);
        PowerRanking powerRanking = powerRankingMapper.powerRankingDTOToPowerRanking(powerRankingDTO);
        powerRanking = powerRankingRepository.save(powerRanking);
        PowerRankingDTO result = powerRankingMapper.powerRankingToPowerRankingDTO(powerRanking);
        return result;
    }

    /**
     *  Get all the powerRankings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PowerRankingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PowerRankings");
        Page<PowerRanking> result = powerRankingRepository.findAll(pageable);
        return result.map(powerRanking -> powerRankingMapper.powerRankingToPowerRankingDTO(powerRanking));
    }

    /**
     *  Get one powerRanking by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PowerRankingDTO findOne(Long id) {
        log.debug("Request to get PowerRanking : {}", id);
        PowerRanking powerRanking = powerRankingRepository.findOne(id);
        PowerRankingDTO powerRankingDTO = powerRankingMapper.powerRankingToPowerRankingDTO(powerRanking);
        return powerRankingDTO;
    }

    /**
     *  Delete the  powerRanking by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PowerRanking : {}", id);
        powerRankingRepository.delete(id);
    }
}
