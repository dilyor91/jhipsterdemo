package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CenterStructure;
import com.mycompany.myapp.repository.CenterStructureRepository;
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
 * Integration tests for the {@link CenterStructureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CenterStructureResourceIT {

    private static final String DEFAULT_CONTENT_UZ = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_RU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_RU = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_KR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_KR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/center-structures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CenterStructureRepository centerStructureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCenterStructureMockMvc;

    private CenterStructure centerStructure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterStructure createEntity(EntityManager em) {
        CenterStructure centerStructure = new CenterStructure()
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR);
        return centerStructure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterStructure createUpdatedEntity(EntityManager em) {
        CenterStructure centerStructure = new CenterStructure()
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR);
        return centerStructure;
    }

    @BeforeEach
    public void initTest() {
        centerStructure = createEntity(em);
    }

    @Test
    @Transactional
    void createCenterStructure() throws Exception {
        int databaseSizeBeforeCreate = centerStructureRepository.findAll().size();
        // Create the CenterStructure
        restCenterStructureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isCreated());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeCreate + 1);
        CenterStructure testCenterStructure = centerStructureList.get(centerStructureList.size() - 1);
        assertThat(testCenterStructure.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testCenterStructure.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testCenterStructure.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
    }

    @Test
    @Transactional
    void createCenterStructureWithExistingId() throws Exception {
        // Create the CenterStructure with an existing ID
        centerStructure.setId(1L);

        int databaseSizeBeforeCreate = centerStructureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterStructureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCenterStructures() throws Exception {
        // Initialize the database
        centerStructureRepository.saveAndFlush(centerStructure);

        // Get all the centerStructureList
        restCenterStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centerStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)));
    }

    @Test
    @Transactional
    void getCenterStructure() throws Exception {
        // Initialize the database
        centerStructureRepository.saveAndFlush(centerStructure);

        // Get the centerStructure
        restCenterStructureMockMvc
            .perform(get(ENTITY_API_URL_ID, centerStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centerStructure.getId().intValue()))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR));
    }

    @Test
    @Transactional
    void getNonExistingCenterStructure() throws Exception {
        // Get the centerStructure
        restCenterStructureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCenterStructure() throws Exception {
        // Initialize the database
        centerStructureRepository.saveAndFlush(centerStructure);

        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();

        // Update the centerStructure
        CenterStructure updatedCenterStructure = centerStructureRepository.findById(centerStructure.getId()).get();
        // Disconnect from session so that the updates on updatedCenterStructure are not directly saved in db
        em.detach(updatedCenterStructure);
        updatedCenterStructure.contentUz(UPDATED_CONTENT_UZ).contentRu(UPDATED_CONTENT_RU).contentKr(UPDATED_CONTENT_KR);

        restCenterStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCenterStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCenterStructure))
            )
            .andExpect(status().isOk());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
        CenterStructure testCenterStructure = centerStructureList.get(centerStructureList.size() - 1);
        assertThat(testCenterStructure.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testCenterStructure.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testCenterStructure.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
    }

    @Test
    @Transactional
    void putNonExistingCenterStructure() throws Exception {
        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();
        centerStructure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centerStructure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCenterStructure() throws Exception {
        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();
        centerStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCenterStructure() throws Exception {
        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();
        centerStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterStructureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCenterStructureWithPatch() throws Exception {
        // Initialize the database
        centerStructureRepository.saveAndFlush(centerStructure);

        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();

        // Update the centerStructure using partial update
        CenterStructure partialUpdatedCenterStructure = new CenterStructure();
        partialUpdatedCenterStructure.setId(centerStructure.getId());

        partialUpdatedCenterStructure.contentUz(UPDATED_CONTENT_UZ).contentKr(UPDATED_CONTENT_KR);

        restCenterStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCenterStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCenterStructure))
            )
            .andExpect(status().isOk());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
        CenterStructure testCenterStructure = centerStructureList.get(centerStructureList.size() - 1);
        assertThat(testCenterStructure.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testCenterStructure.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testCenterStructure.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
    }

    @Test
    @Transactional
    void fullUpdateCenterStructureWithPatch() throws Exception {
        // Initialize the database
        centerStructureRepository.saveAndFlush(centerStructure);

        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();

        // Update the centerStructure using partial update
        CenterStructure partialUpdatedCenterStructure = new CenterStructure();
        partialUpdatedCenterStructure.setId(centerStructure.getId());

        partialUpdatedCenterStructure.contentUz(UPDATED_CONTENT_UZ).contentRu(UPDATED_CONTENT_RU).contentKr(UPDATED_CONTENT_KR);

        restCenterStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCenterStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCenterStructure))
            )
            .andExpect(status().isOk());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
        CenterStructure testCenterStructure = centerStructureList.get(centerStructureList.size() - 1);
        assertThat(testCenterStructure.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testCenterStructure.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testCenterStructure.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
    }

    @Test
    @Transactional
    void patchNonExistingCenterStructure() throws Exception {
        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();
        centerStructure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centerStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCenterStructure() throws Exception {
        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();
        centerStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCenterStructure() throws Exception {
        int databaseSizeBeforeUpdate = centerStructureRepository.findAll().size();
        centerStructure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterStructureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerStructure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CenterStructure in the database
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCenterStructure() throws Exception {
        // Initialize the database
        centerStructureRepository.saveAndFlush(centerStructure);

        int databaseSizeBeforeDelete = centerStructureRepository.findAll().size();

        // Delete the centerStructure
        restCenterStructureMockMvc
            .perform(delete(ENTITY_API_URL_ID, centerStructure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CenterStructure> centerStructureList = centerStructureRepository.findAll();
        assertThat(centerStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
