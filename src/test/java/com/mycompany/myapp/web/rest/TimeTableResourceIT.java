package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TimeTable;
import com.mycompany.myapp.repository.TimeTableRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TimeTableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimeTableResourceIT {

    private static final String DEFAULT_TITLE_UZ = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_RU = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_RU = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_KR = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_KR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_UZ = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_RU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_RU = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_KR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_KR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_POSTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/time-tables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeTableMockMvc;

    private TimeTable timeTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTable createEntity(EntityManager em) {
        TimeTable timeTable = new TimeTable()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .postedDate(DEFAULT_POSTED_DATE);
        return timeTable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTable createUpdatedEntity(EntityManager em) {
        TimeTable timeTable = new TimeTable()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);
        return timeTable;
    }

    @BeforeEach
    public void initTest() {
        timeTable = createEntity(em);
    }

    @Test
    @Transactional
    void createTimeTable() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();
        // Create the TimeTable
        restTimeTableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isCreated());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testTimeTable.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testTimeTable.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testTimeTable.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testTimeTable.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testTimeTable.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testTimeTable.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
    }

    @Test
    @Transactional
    void createTimeTableWithExistingId() throws Exception {
        // Create the TimeTable with an existing ID
        timeTable.setId(1L);

        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimeTables() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get all the timeTableList
        restTimeTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get the timeTable
        restTimeTableMockMvc
            .perform(get(ENTITY_API_URL_ID, timeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeTable.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTimeTable() throws Exception {
        // Get the timeTable
        restTimeTableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable
        TimeTable updatedTimeTable = timeTableRepository.findById(timeTable.getId()).get();
        // Disconnect from session so that the updates on updatedTimeTable are not directly saved in db
        em.detach(updatedTimeTable);
        updatedTimeTable
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);

        restTimeTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTimeTable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTimeTable))
            )
            .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTimeTable.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTimeTable.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testTimeTable.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTimeTable.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTimeTable.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testTimeTable.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();
        timeTable.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeTable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeTable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();
        timeTable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeTable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();
        timeTable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeTableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeTableWithPatch() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable using partial update
        TimeTable partialUpdatedTimeTable = new TimeTable();
        partialUpdatedTimeTable.setId(timeTable.getId());

        partialUpdatedTimeTable.titleRu(UPDATED_TITLE_RU).titleKr(UPDATED_TITLE_KR);

        restTimeTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeTable))
            )
            .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testTimeTable.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTimeTable.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testTimeTable.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testTimeTable.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testTimeTable.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testTimeTable.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTimeTableWithPatch() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable using partial update
        TimeTable partialUpdatedTimeTable = new TimeTable();
        partialUpdatedTimeTable.setId(timeTable.getId());

        partialUpdatedTimeTable
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);

        restTimeTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeTable))
            )
            .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTimeTable.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTimeTable.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testTimeTable.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTimeTable.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTimeTable.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testTimeTable.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();
        timeTable.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timeTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeTable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();
        timeTable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeTable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();
        timeTable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeTableMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(timeTable))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeDelete = timeTableRepository.findAll().size();

        // Delete the timeTable
        restTimeTableMockMvc
            .perform(delete(ENTITY_API_URL_ID, timeTable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
