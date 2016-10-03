package io.github.mikeyfreake.web.rest;

import io.github.mikeyfreake.PiratesflApp;

import io.github.mikeyfreake.domain.TrophyWinner;
import io.github.mikeyfreake.domain.Season;
import io.github.mikeyfreake.domain.Team;
import io.github.mikeyfreake.domain.Trophy;
import io.github.mikeyfreake.repository.TrophyWinnerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TrophyWinnerResource REST controller.
 *
 * @see TrophyWinnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PiratesflApp.class)
public class TrophyWinnerResourceIntTest {


    @Inject
    private TrophyWinnerRepository trophyWinnerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTrophyWinnerMockMvc;

    private TrophyWinner trophyWinner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrophyWinnerResource trophyWinnerResource = new TrophyWinnerResource();
        ReflectionTestUtils.setField(trophyWinnerResource, "trophyWinnerRepository", trophyWinnerRepository);
        this.restTrophyWinnerMockMvc = MockMvcBuilders.standaloneSetup(trophyWinnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrophyWinner createEntity(EntityManager em) {
        TrophyWinner trophyWinner = new TrophyWinner();
        // Add required entity
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        trophyWinner.setSeason(season);
        // Add required entity
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        trophyWinner.setTeam(team);
        // Add required entity
        Trophy trophy = TrophyResourceIntTest.createEntity(em);
        em.persist(trophy);
        em.flush();
        trophyWinner.setTrophy(trophy);
        return trophyWinner;
    }

    @Before
    public void initTest() {
        trophyWinner = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrophyWinner() throws Exception {
        int databaseSizeBeforeCreate = trophyWinnerRepository.findAll().size();

        // Create the TrophyWinner

        restTrophyWinnerMockMvc.perform(post("/api/trophy-winners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trophyWinner)))
                .andExpect(status().isCreated());

        // Validate the TrophyWinner in the database
        List<TrophyWinner> trophyWinners = trophyWinnerRepository.findAll();
        assertThat(trophyWinners).hasSize(databaseSizeBeforeCreate + 1);
        TrophyWinner testTrophyWinner = trophyWinners.get(trophyWinners.size() - 1);
    }

    @Test
    @Transactional
    public void getAllTrophyWinners() throws Exception {
        // Initialize the database
        trophyWinnerRepository.saveAndFlush(trophyWinner);

        // Get all the trophyWinners
        restTrophyWinnerMockMvc.perform(get("/api/trophy-winners?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trophyWinner.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTrophyWinner() throws Exception {
        // Initialize the database
        trophyWinnerRepository.saveAndFlush(trophyWinner);

        // Get the trophyWinner
        restTrophyWinnerMockMvc.perform(get("/api/trophy-winners/{id}", trophyWinner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trophyWinner.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrophyWinner() throws Exception {
        // Get the trophyWinner
        restTrophyWinnerMockMvc.perform(get("/api/trophy-winners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrophyWinner() throws Exception {
        // Initialize the database
        trophyWinnerRepository.saveAndFlush(trophyWinner);
        int databaseSizeBeforeUpdate = trophyWinnerRepository.findAll().size();

        // Update the trophyWinner
        TrophyWinner updatedTrophyWinner = trophyWinnerRepository.findOne(trophyWinner.getId());

        restTrophyWinnerMockMvc.perform(put("/api/trophy-winners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTrophyWinner)))
                .andExpect(status().isOk());

        // Validate the TrophyWinner in the database
        List<TrophyWinner> trophyWinners = trophyWinnerRepository.findAll();
        assertThat(trophyWinners).hasSize(databaseSizeBeforeUpdate);
        TrophyWinner testTrophyWinner = trophyWinners.get(trophyWinners.size() - 1);
    }

    @Test
    @Transactional
    public void deleteTrophyWinner() throws Exception {
        // Initialize the database
        trophyWinnerRepository.saveAndFlush(trophyWinner);
        int databaseSizeBeforeDelete = trophyWinnerRepository.findAll().size();

        // Get the trophyWinner
        restTrophyWinnerMockMvc.perform(delete("/api/trophy-winners/{id}", trophyWinner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TrophyWinner> trophyWinners = trophyWinnerRepository.findAll();
        assertThat(trophyWinners).hasSize(databaseSizeBeforeDelete - 1);
    }
}
