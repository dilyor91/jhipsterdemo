package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaterialTopicLevel;
import com.mycompany.myapp.repository.MaterialTopicLevelRepository;
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
 * Integration tests for the {@link MaterialTopicLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialTopicLevelResourceIT {

    private static final String DEFAULT_TITLE_UZ = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_RU = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_RU = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_KR = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_KR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/material-topic-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialTopicLevelRepository materialTopicLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialTopicLevelMockMvc;

    private MaterialTopicLevel materialTopicLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialTopicLevel createEntity(EntityManager em) {
        MaterialTopicLevel materialTopicLevel = new MaterialTopicLevel()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR);
        return materialTopicLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialTopicLevel createUpdatedEntity(EntityManager em) {
        MaterialTopicLevel materialTopicLevel = new MaterialTopicLevel()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR);
        return materialTopicLevel;
    }

    @BeforeEach
    public void initTest() {
        materialTopicLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeCreate = materialTopicLevelRepository.findAll().size();
        // Create the MaterialTopicLevel
        restMaterialTopicLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isCreated());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialTopicLevel testMaterialTopicLevel = materialTopicLevelList.get(materialTopicLevelList.size() - 1);
        assertThat(testMaterialTopicLevel.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testMaterialTopicLevel.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testMaterialTopicLevel.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
    }

    @Test
    @Transactional
    void createMaterialTopicLevelWithExistingId() throws Exception {
        // Create the MaterialTopicLevel with an existing ID
        materialTopicLevel.setId(1L);

        int databaseSizeBeforeCreate = materialTopicLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialTopicLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterialTopicLevels() throws Exception {
        // Initialize the database
        materialTopicLevelRepository.saveAndFlush(materialTopicLevel);

        // Get all the materialTopicLevelList
        restMaterialTopicLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialTopicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)));
    }

    @Test
    @Transactional
    void getMaterialTopicLevel() throws Exception {
        // Initialize the database
        materialTopicLevelRepository.saveAndFlush(materialTopicLevel);

        // Get the materialTopicLevel
        restMaterialTopicLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, materialTopicLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialTopicLevel.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR));
    }

    @Test
    @Transactional
    void getNonExistingMaterialTopicLevel() throws Exception {
        // Get the materialTopicLevel
        restMaterialTopicLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterialTopicLevel() throws Exception {
        // Initialize the database
        materialTopicLevelRepository.saveAndFlush(materialTopicLevel);

        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();

        // Update the materialTopicLevel
        MaterialTopicLevel updatedMaterialTopicLevel = materialTopicLevelRepository.findById(materialTopicLevel.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialTopicLevel are not directly saved in db
        em.detach(updatedMaterialTopicLevel);
        updatedMaterialTopicLevel.titleUz(UPDATED_TITLE_UZ).titleRu(UPDATED_TITLE_RU).titleKr(UPDATED_TITLE_KR);

        restMaterialTopicLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterialTopicLevel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterialTopicLevel))
            )
            .andExpect(status().isOk());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
        MaterialTopicLevel testMaterialTopicLevel = materialTopicLevelList.get(materialTopicLevelList.size() - 1);
        assertThat(testMaterialTopicLevel.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testMaterialTopicLevel.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testMaterialTopicLevel.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
    }

    @Test
    @Transactional
    void putNonExistingMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();
        materialTopicLevel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialTopicLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materialTopicLevel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();
        materialTopicLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();
        materialTopicLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicLevelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialTopicLevelWithPatch() throws Exception {
        // Initialize the database
        materialTopicLevelRepository.saveAndFlush(materialTopicLevel);

        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();

        // Update the materialTopicLevel using partial update
        MaterialTopicLevel partialUpdatedMaterialTopicLevel = new MaterialTopicLevel();
        partialUpdatedMaterialTopicLevel.setId(materialTopicLevel.getId());

        partialUpdatedMaterialTopicLevel.titleKr(UPDATED_TITLE_KR);

        restMaterialTopicLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialTopicLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialTopicLevel))
            )
            .andExpect(status().isOk());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
        MaterialTopicLevel testMaterialTopicLevel = materialTopicLevelList.get(materialTopicLevelList.size() - 1);
        assertThat(testMaterialTopicLevel.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testMaterialTopicLevel.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testMaterialTopicLevel.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
    }

    @Test
    @Transactional
    void fullUpdateMaterialTopicLevelWithPatch() throws Exception {
        // Initialize the database
        materialTopicLevelRepository.saveAndFlush(materialTopicLevel);

        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();

        // Update the materialTopicLevel using partial update
        MaterialTopicLevel partialUpdatedMaterialTopicLevel = new MaterialTopicLevel();
        partialUpdatedMaterialTopicLevel.setId(materialTopicLevel.getId());

        partialUpdatedMaterialTopicLevel.titleUz(UPDATED_TITLE_UZ).titleRu(UPDATED_TITLE_RU).titleKr(UPDATED_TITLE_KR);

        restMaterialTopicLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialTopicLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialTopicLevel))
            )
            .andExpect(status().isOk());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
        MaterialTopicLevel testMaterialTopicLevel = materialTopicLevelList.get(materialTopicLevelList.size() - 1);
        assertThat(testMaterialTopicLevel.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testMaterialTopicLevel.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testMaterialTopicLevel.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
    }

    @Test
    @Transactional
    void patchNonExistingMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();
        materialTopicLevel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialTopicLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materialTopicLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();
        materialTopicLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterialTopicLevel() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicLevelRepository.findAll().size();
        materialTopicLevel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicLevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialTopicLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialTopicLevel in the database
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterialTopicLevel() throws Exception {
        // Initialize the database
        materialTopicLevelRepository.saveAndFlush(materialTopicLevel);

        int databaseSizeBeforeDelete = materialTopicLevelRepository.findAll().size();

        // Delete the materialTopicLevel
        restMaterialTopicLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, materialTopicLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialTopicLevel> materialTopicLevelList = materialTopicLevelRepository.findAll();
        assertThat(materialTopicLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
