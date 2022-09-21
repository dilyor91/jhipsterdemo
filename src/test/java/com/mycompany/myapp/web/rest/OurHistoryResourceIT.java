package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.OurHistory;
import com.mycompany.myapp.repository.OurHistoryRepository;
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
 * Integration tests for the {@link OurHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OurHistoryResourceIT {

    private static final String DEFAULT_CONTENT_UZ = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_RU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_RU = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_KR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_KR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_POSTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/our-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OurHistoryRepository ourHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOurHistoryMockMvc;

    private OurHistory ourHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OurHistory createEntity(EntityManager em) {
        OurHistory ourHistory = new OurHistory()
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .postedDate(DEFAULT_POSTED_DATE);
        return ourHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OurHistory createUpdatedEntity(EntityManager em) {
        OurHistory ourHistory = new OurHistory()
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);
        return ourHistory;
    }

    @BeforeEach
    public void initTest() {
        ourHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createOurHistory() throws Exception {
        int databaseSizeBeforeCreate = ourHistoryRepository.findAll().size();
        // Create the OurHistory
        restOurHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ourHistory)))
            .andExpect(status().isCreated());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        OurHistory testOurHistory = ourHistoryList.get(ourHistoryList.size() - 1);
        assertThat(testOurHistory.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testOurHistory.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testOurHistory.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testOurHistory.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
    }

    @Test
    @Transactional
    void createOurHistoryWithExistingId() throws Exception {
        // Create the OurHistory with an existing ID
        ourHistory.setId(1L);

        int databaseSizeBeforeCreate = ourHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOurHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ourHistory)))
            .andExpect(status().isBadRequest());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOurHistories() throws Exception {
        // Initialize the database
        ourHistoryRepository.saveAndFlush(ourHistory);

        // Get all the ourHistoryList
        restOurHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ourHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOurHistory() throws Exception {
        // Initialize the database
        ourHistoryRepository.saveAndFlush(ourHistory);

        // Get the ourHistory
        restOurHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, ourHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ourHistory.getId().intValue()))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOurHistory() throws Exception {
        // Get the ourHistory
        restOurHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOurHistory() throws Exception {
        // Initialize the database
        ourHistoryRepository.saveAndFlush(ourHistory);

        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();

        // Update the ourHistory
        OurHistory updatedOurHistory = ourHistoryRepository.findById(ourHistory.getId()).get();
        // Disconnect from session so that the updates on updatedOurHistory are not directly saved in db
        em.detach(updatedOurHistory);
        updatedOurHistory
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);

        restOurHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOurHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOurHistory))
            )
            .andExpect(status().isOk());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
        OurHistory testOurHistory = ourHistoryList.get(ourHistoryList.size() - 1);
        assertThat(testOurHistory.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testOurHistory.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testOurHistory.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testOurHistory.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOurHistory() throws Exception {
        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();
        ourHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOurHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ourHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ourHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOurHistory() throws Exception {
        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();
        ourHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOurHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ourHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOurHistory() throws Exception {
        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();
        ourHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOurHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ourHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOurHistoryWithPatch() throws Exception {
        // Initialize the database
        ourHistoryRepository.saveAndFlush(ourHistory);

        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();

        // Update the ourHistory using partial update
        OurHistory partialUpdatedOurHistory = new OurHistory();
        partialUpdatedOurHistory.setId(ourHistory.getId());

        partialUpdatedOurHistory.contentKr(UPDATED_CONTENT_KR);

        restOurHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOurHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOurHistory))
            )
            .andExpect(status().isOk());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
        OurHistory testOurHistory = ourHistoryList.get(ourHistoryList.size() - 1);
        assertThat(testOurHistory.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testOurHistory.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testOurHistory.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testOurHistory.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOurHistoryWithPatch() throws Exception {
        // Initialize the database
        ourHistoryRepository.saveAndFlush(ourHistory);

        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();

        // Update the ourHistory using partial update
        OurHistory partialUpdatedOurHistory = new OurHistory();
        partialUpdatedOurHistory.setId(ourHistory.getId());

        partialUpdatedOurHistory
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);

        restOurHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOurHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOurHistory))
            )
            .andExpect(status().isOk());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
        OurHistory testOurHistory = ourHistoryList.get(ourHistoryList.size() - 1);
        assertThat(testOurHistory.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testOurHistory.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testOurHistory.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testOurHistory.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOurHistory() throws Exception {
        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();
        ourHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOurHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ourHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ourHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOurHistory() throws Exception {
        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();
        ourHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOurHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ourHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOurHistory() throws Exception {
        int databaseSizeBeforeUpdate = ourHistoryRepository.findAll().size();
        ourHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOurHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ourHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OurHistory in the database
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOurHistory() throws Exception {
        // Initialize the database
        ourHistoryRepository.saveAndFlush(ourHistory);

        int databaseSizeBeforeDelete = ourHistoryRepository.findAll().size();

        // Delete the ourHistory
        restOurHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, ourHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OurHistory> ourHistoryList = ourHistoryRepository.findAll();
        assertThat(ourHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
