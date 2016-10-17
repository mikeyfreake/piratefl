package io.github.mikeyfreake.web.rest;

import io.github.mikeyfreake.PiratesflApp;

import io.github.mikeyfreake.domain.PowerRanking;
import io.github.mikeyfreake.domain.Team;
import io.github.mikeyfreake.domain.Season;
import io.github.mikeyfreake.repository.PowerRankingRepository;

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
 * Test class for the PowerRankingResource REST controller.
 *
 * @see PowerRankingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PiratesflApp.class)
public class PowerRankingResourceIntTest {


    private static final Integer DEFAULT_WEEK = 1;
    private static final Integer UPDATED_WEEK = 2;

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;
    private static final String DEFAULT_COMMENTS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private PowerRankingRepository powerRankingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPowerRankingMockMvc;

    private PowerRanking powerRanking;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerRankingResource powerRankingResource = new PowerRankingResource();
        ReflectionTestUtils.setField(powerRankingResource, "powerRankingRepository", powerRankingRepository);
        this.restPowerRankingMockMvc = MockMvcBuilders.standaloneSetup(powerRankingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerRanking createEntity(EntityManager em) {
        PowerRanking powerRanking = new PowerRanking()
                .week(DEFAULT_WEEK)
                .rank(DEFAULT_RANK)
                .comments(DEFAULT_COMMENTS);
        // Add required entity
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        powerRanking.setTeam(team);
        // Add required entity
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        powerRanking.setSeason(season);
        return powerRanking;
    }

    @Before
    public void initTest() {
        powerRanking = createEntity(em);
    }

    @Test
    @Transactional
    public void createPowerRanking() throws Exception {
        int databaseSizeBeforeCreate = powerRankingRepository.findAll().size();

        // Create the PowerRanking

        restPowerRankingMockMvc.perform(post("/api/power-rankings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(powerRanking)))
                .andExpect(status().isCreated());

        // Validate the PowerRanking in the database
        List<PowerRanking> powerRankings = powerRankingRepository.findAll();
        assertThat(powerRankings).hasSize(databaseSizeBeforeCreate + 1);
        PowerRanking testPowerRanking = powerRankings.get(powerRankings.size() - 1);
        assertThat(testPowerRanking.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testPowerRanking.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testPowerRanking.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void checkWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerRankingRepository.findAll().size();
        // set the field null
        powerRanking.setWeek(null);

        // Create the PowerRanking, which fails.

        restPowerRankingMockMvc.perform(post("/api/power-rankings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(powerRanking)))
                .andExpect(status().isBadRequest());

        List<PowerRanking> powerRankings = powerRankingRepository.findAll();
        assertThat(powerRankings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRankIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerRankingRepository.findAll().size();
        // set the field null
        powerRanking.setRank(null);

        // Create the PowerRanking, which fails.

        restPowerRankingMockMvc.perform(post("/api/power-rankings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(powerRanking)))
                .andExpect(status().isBadRequest());

        List<PowerRanking> powerRankings = powerRankingRepository.findAll();
        assertThat(powerRankings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPowerRankings() throws Exception {
        // Initialize the database
        powerRankingRepository.saveAndFlush(powerRanking);

        // Get all the powerRankings
        restPowerRankingMockMvc.perform(get("/api/power-rankings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(powerRanking.getId().intValue())))
                .andExpect(jsonPath("$.[*].week").value(hasItem(DEFAULT_WEEK)))
                .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getPowerRanking() throws Exception {
        // Initialize the database
        powerRankingRepository.saveAndFlush(powerRanking);

        // Get the powerRanking
        restPowerRankingMockMvc.perform(get("/api/power-rankings/{id}", powerRanking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(powerRanking.getId().intValue()))
            .andExpect(jsonPath("$.week").value(DEFAULT_WEEK))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPowerRanking() throws Exception {
        // Get the powerRanking
        restPowerRankingMockMvc.perform(get("/api/power-rankings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePowerRanking() throws Exception {
        // Initialize the database
        powerRankingRepository.saveAndFlush(powerRanking);
        int databaseSizeBeforeUpdate = powerRankingRepository.findAll().size();

        // Update the powerRanking
        PowerRanking updatedPowerRanking = powerRankingRepository.findOne(powerRanking.getId());
        updatedPowerRanking
                .week(UPDATED_WEEK)
                .rank(UPDATED_RANK)
                .comments(UPDATED_COMMENTS);

        restPowerRankingMockMvc.perform(put("/api/power-rankings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPowerRanking)))
                .andExpect(status().isOk());

        // Validate the PowerRanking in the database
        List<PowerRanking> powerRankings = powerRankingRepository.findAll();
        assertThat(powerRankings).hasSize(databaseSizeBeforeUpdate);
        PowerRanking testPowerRanking = powerRankings.get(powerRankings.size() - 1);
        assertThat(testPowerRanking.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testPowerRanking.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testPowerRanking.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void deletePowerRanking() throws Exception {
        // Initialize the database
        powerRankingRepository.saveAndFlush(powerRanking);
        int databaseSizeBeforeDelete = powerRankingRepository.findAll().size();

        // Get the powerRanking
        restPowerRankingMockMvc.perform(delete("/api/power-rankings/{id}", powerRanking.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PowerRanking> powerRankings = powerRankingRepository.findAll();
        assertThat(powerRankings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
