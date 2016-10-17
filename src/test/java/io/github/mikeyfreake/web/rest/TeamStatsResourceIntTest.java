package io.github.mikeyfreake.web.rest;

import io.github.mikeyfreake.PiratesflApp;

import io.github.mikeyfreake.domain.TeamStats;
import io.github.mikeyfreake.domain.Season;
import io.github.mikeyfreake.domain.Team;
import io.github.mikeyfreake.repository.TeamStatsRepository;

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
 * Test class for the TeamStatsResource REST controller.
 *
 * @see TeamStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PiratesflApp.class)
public class TeamStatsResourceIntTest {

    private static final Integer DEFAULT_WINS = 1;
    private static final Integer UPDATED_WINS = 2;

    private static final Integer DEFAULT_LOSSES = 1;
    private static final Integer UPDATED_LOSSES = 2;

    private static final Integer DEFAULT_TIES = 1;
    private static final Integer UPDATED_TIES = 2;

    private static final Integer DEFAULT_POINTS_FOR = 1;
    private static final Integer UPDATED_POINTS_FOR = 2;

    private static final Integer DEFAULT_POINTS_AGAINST = 1;
    private static final Integer UPDATED_POINTS_AGAINST = 2;

    private static final Integer DEFAULT_DRAFT_POSITION = 1;
    private static final Integer UPDATED_DRAFT_POSITION = 2;

    private static final Integer DEFAULT_FINISHED = 1;
    private static final Integer UPDATED_FINISHED = 2;

    @Inject
    private TeamStatsRepository teamStatsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTeamStatsMockMvc;

    private TeamStats teamStats;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeamStatsResource teamStatsResource = new TeamStatsResource();
        ReflectionTestUtils.setField(teamStatsResource, "teamStatsRepository", teamStatsRepository);
        this.restTeamStatsMockMvc = MockMvcBuilders.standaloneSetup(teamStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamStats createEntity(EntityManager em) {
        TeamStats teamStats = new TeamStats()
                .wins(DEFAULT_WINS)
                .losses(DEFAULT_LOSSES)
                .ties(DEFAULT_TIES)
                .pointsFor(DEFAULT_POINTS_FOR)
                .pointsAgainst(DEFAULT_POINTS_AGAINST)
                .draftPosition(DEFAULT_DRAFT_POSITION)
                .finished(DEFAULT_FINISHED);
        // Add required entity
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        teamStats.setSeason(season);
        // Add required entity
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        teamStats.setTeam(team);
        return teamStats;
    }

    @Before
    public void initTest() {
        teamStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamStats() throws Exception {
        int databaseSizeBeforeCreate = teamStatsRepository.findAll().size();

        // Create the TeamStats

        restTeamStatsMockMvc.perform(post("/api/team-stats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teamStats)))
                .andExpect(status().isCreated());

        // Validate the TeamStats in the database
        List<TeamStats> teamStats = teamStatsRepository.findAll();
        assertThat(teamStats).hasSize(databaseSizeBeforeCreate + 1);
        TeamStats testTeamStats = teamStats.get(teamStats.size() - 1);
        assertThat(testTeamStats.getWins()).isEqualTo(DEFAULT_WINS);
        assertThat(testTeamStats.getLosses()).isEqualTo(DEFAULT_LOSSES);
        assertThat(testTeamStats.getTies()).isEqualTo(DEFAULT_TIES);
        assertThat(testTeamStats.getPointsFor()).isEqualTo(DEFAULT_POINTS_FOR);
        assertThat(testTeamStats.getPointsAgainst()).isEqualTo(DEFAULT_POINTS_AGAINST);
        assertThat(testTeamStats.getDraftPosition()).isEqualTo(DEFAULT_DRAFT_POSITION);
        assertThat(testTeamStats.getFinished()).isEqualTo(DEFAULT_FINISHED);
    }

    @Test
    @Transactional
    public void getAllTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);

        // Get all the teamStats
        restTeamStatsMockMvc.perform(get("/api/team-stats?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(teamStats.getId().intValue())))
                .andExpect(jsonPath("$.[*].wins").value(hasItem(DEFAULT_WINS)))
                .andExpect(jsonPath("$.[*].losses").value(hasItem(DEFAULT_LOSSES)))
                .andExpect(jsonPath("$.[*].ties").value(hasItem(DEFAULT_TIES)))
                .andExpect(jsonPath("$.[*].pointsFor").value(hasItem(DEFAULT_POINTS_FOR)))
                .andExpect(jsonPath("$.[*].pointsAgainst").value(hasItem(DEFAULT_POINTS_AGAINST)))
                .andExpect(jsonPath("$.[*].draftPosition").value(hasItem(DEFAULT_DRAFT_POSITION)))
                .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED)));
    }

    @Test
    @Transactional
    public void getTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);

        // Get the teamStats
        restTeamStatsMockMvc.perform(get("/api/team-stats/{id}", teamStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamStats.getId().intValue()))
            .andExpect(jsonPath("$.wins").value(DEFAULT_WINS))
            .andExpect(jsonPath("$.losses").value(DEFAULT_LOSSES))
            .andExpect(jsonPath("$.ties").value(DEFAULT_TIES))
            .andExpect(jsonPath("$.pointsFor").value(DEFAULT_POINTS_FOR))
            .andExpect(jsonPath("$.pointsAgainst").value(DEFAULT_POINTS_AGAINST))
            .andExpect(jsonPath("$.draftPosition").value(DEFAULT_DRAFT_POSITION))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED));
    }

    @Test
    @Transactional
    public void getNonExistingTeamStats() throws Exception {
        // Get the teamStats
        restTeamStatsMockMvc.perform(get("/api/team-stats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);
        int databaseSizeBeforeUpdate = teamStatsRepository.findAll().size();

        // Update the teamStats
        TeamStats updatedTeamStats = teamStatsRepository.findOne(teamStats.getId());
        updatedTeamStats
                .wins(UPDATED_WINS)
                .losses(UPDATED_LOSSES)
                .ties(UPDATED_TIES)
                .pointsFor(UPDATED_POINTS_FOR)
                .pointsAgainst(UPDATED_POINTS_AGAINST)
                .draftPosition(UPDATED_DRAFT_POSITION)
                .finished(UPDATED_FINISHED);

        restTeamStatsMockMvc.perform(put("/api/team-stats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTeamStats)))
                .andExpect(status().isOk());

        // Validate the TeamStats in the database
        List<TeamStats> teamStats = teamStatsRepository.findAll();
        assertThat(teamStats).hasSize(databaseSizeBeforeUpdate);
        TeamStats testTeamStats = teamStats.get(teamStats.size() - 1);
        assertThat(testTeamStats.getWins()).isEqualTo(UPDATED_WINS);
        assertThat(testTeamStats.getLosses()).isEqualTo(UPDATED_LOSSES);
        assertThat(testTeamStats.getTies()).isEqualTo(UPDATED_TIES);
        assertThat(testTeamStats.getPointsFor()).isEqualTo(UPDATED_POINTS_FOR);
        assertThat(testTeamStats.getPointsAgainst()).isEqualTo(UPDATED_POINTS_AGAINST);
        assertThat(testTeamStats.getDraftPosition()).isEqualTo(UPDATED_DRAFT_POSITION);
        assertThat(testTeamStats.getFinished()).isEqualTo(UPDATED_FINISHED);
    }

    @Test
    @Transactional
    public void deleteTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);
        int databaseSizeBeforeDelete = teamStatsRepository.findAll().size();

        // Get the teamStats
        restTeamStatsMockMvc.perform(delete("/api/team-stats/{id}", teamStats.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamStats> teamStats = teamStatsRepository.findAll();
        assertThat(teamStats).hasSize(databaseSizeBeforeDelete - 1);
    }
}
