package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaterialTopic;
import com.mycompany.myapp.repository.MaterialTopicRepository;
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
 * Integration tests for the {@link MaterialTopicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialTopicResourceIT {

    private static final String DEFAULT_TITLE_UZ = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_RU = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_RU = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_KR = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_KR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/material-topics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialTopicRepository materialTopicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialTopicMockMvc;

    private MaterialTopic materialTopic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialTopic createEntity(EntityManager em) {
        MaterialTopic materialTopic = new MaterialTopic().titleUz(DEFAULT_TITLE_UZ).titleRu(DEFAULT_TITLE_RU).titleKr(DEFAULT_TITLE_KR);
        return materialTopic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialTopic createUpdatedEntity(EntityManager em) {
        MaterialTopic materialTopic = new MaterialTopic().titleUz(UPDATED_TITLE_UZ).titleRu(UPDATED_TITLE_RU).titleKr(UPDATED_TITLE_KR);
        return materialTopic;
    }

    @BeforeEach
    public void initTest() {
        materialTopic = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterialTopic() throws Exception {
        int databaseSizeBeforeCreate = materialTopicRepository.findAll().size();
        // Create the MaterialTopic
        restMaterialTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialTopic)))
            .andExpect(status().isCreated());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialTopic testMaterialTopic = materialTopicList.get(materialTopicList.size() - 1);
        assertThat(testMaterialTopic.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testMaterialTopic.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testMaterialTopic.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
    }

    @Test
    @Transactional
    void createMaterialTopicWithExistingId() throws Exception {
        // Create the MaterialTopic with an existing ID
        materialTopic.setId(1L);

        int databaseSizeBeforeCreate = materialTopicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialTopic)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterialTopics() throws Exception {
        // Initialize the database
        materialTopicRepository.saveAndFlush(materialTopic);

        // Get all the materialTopicList
        restMaterialTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialTopic.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)));
    }

    @Test
    @Transactional
    void getMaterialTopic() throws Exception {
        // Initialize the database
        materialTopicRepository.saveAndFlush(materialTopic);

        // Get the materialTopic
        restMaterialTopicMockMvc
            .perform(get(ENTITY_API_URL_ID, materialTopic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialTopic.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR));
    }

    @Test
    @Transactional
    void getNonExistingMaterialTopic() throws Exception {
        // Get the materialTopic
        restMaterialTopicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterialTopic() throws Exception {
        // Initialize the database
        materialTopicRepository.saveAndFlush(materialTopic);

        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();

        // Update the materialTopic
        MaterialTopic updatedMaterialTopic = materialTopicRepository.findById(materialTopic.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialTopic are not directly saved in db
        em.detach(updatedMaterialTopic);
        updatedMaterialTopic.titleUz(UPDATED_TITLE_UZ).titleRu(UPDATED_TITLE_RU).titleKr(UPDATED_TITLE_KR);

        restMaterialTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterialTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterialTopic))
            )
            .andExpect(status().isOk());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
        MaterialTopic testMaterialTopic = materialTopicList.get(materialTopicList.size() - 1);
        assertThat(testMaterialTopic.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testMaterialTopic.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testMaterialTopic.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
    }

    @Test
    @Transactional
    void putNonExistingMaterialTopic() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();
        materialTopic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materialTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterialTopic() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();
        materialTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterialTopic() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();
        materialTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialTopic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialTopicWithPatch() throws Exception {
        // Initialize the database
        materialTopicRepository.saveAndFlush(materialTopic);

        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();

        // Update the materialTopic using partial update
        MaterialTopic partialUpdatedMaterialTopic = new MaterialTopic();
        partialUpdatedMaterialTopic.setId(materialTopic.getId());

        partialUpdatedMaterialTopic.titleRu(UPDATED_TITLE_RU);

        restMaterialTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialTopic))
            )
            .andExpect(status().isOk());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
        MaterialTopic testMaterialTopic = materialTopicList.get(materialTopicList.size() - 1);
        assertThat(testMaterialTopic.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testMaterialTopic.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testMaterialTopic.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
    }

    @Test
    @Transactional
    void fullUpdateMaterialTopicWithPatch() throws Exception {
        // Initialize the database
        materialTopicRepository.saveAndFlush(materialTopic);

        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();

        // Update the materialTopic using partial update
        MaterialTopic partialUpdatedMaterialTopic = new MaterialTopic();
        partialUpdatedMaterialTopic.setId(materialTopic.getId());

        partialUpdatedMaterialTopic.titleUz(UPDATED_TITLE_UZ).titleRu(UPDATED_TITLE_RU).titleKr(UPDATED_TITLE_KR);

        restMaterialTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialTopic))
            )
            .andExpect(status().isOk());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
        MaterialTopic testMaterialTopic = materialTopicList.get(materialTopicList.size() - 1);
        assertThat(testMaterialTopic.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testMaterialTopic.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testMaterialTopic.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
    }

    @Test
    @Transactional
    void patchNonExistingMaterialTopic() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();
        materialTopic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materialTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterialTopic() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();
        materialTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterialTopic() throws Exception {
        int databaseSizeBeforeUpdate = materialTopicRepository.findAll().size();
        materialTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialTopicMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(materialTopic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialTopic in the database
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterialTopic() throws Exception {
        // Initialize the database
        materialTopicRepository.saveAndFlush(materialTopic);

        int databaseSizeBeforeDelete = materialTopicRepository.findAll().size();

        // Delete the materialTopic
        restMaterialTopicMockMvc
            .perform(delete(ENTITY_API_URL_ID, materialTopic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialTopic> materialTopicList = materialTopicRepository.findAll();
        assertThat(materialTopicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
