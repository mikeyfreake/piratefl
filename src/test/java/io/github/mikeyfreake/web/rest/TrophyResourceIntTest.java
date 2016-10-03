package io.github.mikeyfreake.web.rest;

import io.github.mikeyfreake.PiratesflApp;

import io.github.mikeyfreake.domain.Trophy;
import io.github.mikeyfreake.domain.League;
import io.github.mikeyfreake.repository.TrophyRepository;

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
 * Test class for the TrophyResource REST controller.
 *
 * @see TrophyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PiratesflApp.class)
public class TrophyResourceIntTest {

    private static final String DEFAULT_TROPHY_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_TROPHY_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private TrophyRepository trophyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTrophyMockMvc;

    private Trophy trophy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrophyResource trophyResource = new TrophyResource();
        ReflectionTestUtils.setField(trophyResource, "trophyRepository", trophyRepository);
        this.restTrophyMockMvc = MockMvcBuilders.standaloneSetup(trophyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trophy createEntity(EntityManager em) {
        Trophy trophy = new Trophy()
                .trophyName(DEFAULT_TROPHY_NAME);
        // Add required entity
        League league = LeagueResourceIntTest.createEntity(em);
        em.persist(league);
        em.flush();
        trophy.setLeague(league);
        return trophy;
    }

    @Before
    public void initTest() {
        trophy = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrophy() throws Exception {
        int databaseSizeBeforeCreate = trophyRepository.findAll().size();

        // Create the Trophy

        restTrophyMockMvc.perform(post("/api/trophies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trophy)))
                .andExpect(status().isCreated());

        // Validate the Trophy in the database
        List<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies).hasSize(databaseSizeBeforeCreate + 1);
        Trophy testTrophy = trophies.get(trophies.size() - 1);
        assertThat(testTrophy.getTrophyName()).isEqualTo(DEFAULT_TROPHY_NAME);
    }

    @Test
    @Transactional
    public void checkTrophyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trophyRepository.findAll().size();
        // set the field null
        trophy.setTrophyName(null);

        // Create the Trophy, which fails.

        restTrophyMockMvc.perform(post("/api/trophies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trophy)))
                .andExpect(status().isBadRequest());

        List<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrophies() throws Exception {
        // Initialize the database
        trophyRepository.saveAndFlush(trophy);

        // Get all the trophies
        restTrophyMockMvc.perform(get("/api/trophies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trophy.getId().intValue())))
                .andExpect(jsonPath("$.[*].trophyName").value(hasItem(DEFAULT_TROPHY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTrophy() throws Exception {
        // Initialize the database
        trophyRepository.saveAndFlush(trophy);

        // Get the trophy
        restTrophyMockMvc.perform(get("/api/trophies/{id}", trophy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trophy.getId().intValue()))
            .andExpect(jsonPath("$.trophyName").value(DEFAULT_TROPHY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrophy() throws Exception {
        // Get the trophy
        restTrophyMockMvc.perform(get("/api/trophies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrophy() throws Exception {
        // Initialize the database
        trophyRepository.saveAndFlush(trophy);
        int databaseSizeBeforeUpdate = trophyRepository.findAll().size();

        // Update the trophy
        Trophy updatedTrophy = trophyRepository.findOne(trophy.getId());
        updatedTrophy
                .trophyName(UPDATED_TROPHY_NAME);

        restTrophyMockMvc.perform(put("/api/trophies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTrophy)))
                .andExpect(status().isOk());

        // Validate the Trophy in the database
        List<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies).hasSize(databaseSizeBeforeUpdate);
        Trophy testTrophy = trophies.get(trophies.size() - 1);
        assertThat(testTrophy.getTrophyName()).isEqualTo(UPDATED_TROPHY_NAME);
    }

    @Test
    @Transactional
    public void deleteTrophy() throws Exception {
        // Initialize the database
        trophyRepository.saveAndFlush(trophy);
        int databaseSizeBeforeDelete = trophyRepository.findAll().size();

        // Get the trophy
        restTrophyMockMvc.perform(delete("/api/trophies/{id}", trophy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Trophy> trophies = trophyRepository.findAll();
        assertThat(trophies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
