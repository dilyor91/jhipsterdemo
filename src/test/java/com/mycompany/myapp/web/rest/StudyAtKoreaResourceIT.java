package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.StudyAtKorea;
import com.mycompany.myapp.repository.StudyAtKoreaRepository;
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
 * Integration tests for the {@link StudyAtKoreaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudyAtKoreaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/study-at-koreas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudyAtKoreaRepository studyAtKoreaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudyAtKoreaMockMvc;

    private StudyAtKorea studyAtKorea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyAtKorea createEntity(EntityManager em) {
        StudyAtKorea studyAtKorea = new StudyAtKorea()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR);
        return studyAtKorea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyAtKorea createUpdatedEntity(EntityManager em) {
        StudyAtKorea studyAtKorea = new StudyAtKorea()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR);
        return studyAtKorea;
    }

    @BeforeEach
    public void initTest() {
        studyAtKorea = createEntity(em);
    }

    @Test
    @Transactional
    void createStudyAtKorea() throws Exception {
        int databaseSizeBeforeCreate = studyAtKoreaRepository.findAll().size();
        // Create the StudyAtKorea
        restStudyAtKoreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studyAtKorea)))
            .andExpect(status().isCreated());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeCreate + 1);
        StudyAtKorea testStudyAtKorea = studyAtKoreaList.get(studyAtKoreaList.size() - 1);
        assertThat(testStudyAtKorea.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testStudyAtKorea.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testStudyAtKorea.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testStudyAtKorea.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testStudyAtKorea.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testStudyAtKorea.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
    }

    @Test
    @Transactional
    void createStudyAtKoreaWithExistingId() throws Exception {
        // Create the StudyAtKorea with an existing ID
        studyAtKorea.setId(1L);

        int databaseSizeBeforeCreate = studyAtKoreaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyAtKoreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studyAtKorea)))
            .andExpect(status().isBadRequest());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudyAtKoreas() throws Exception {
        // Initialize the database
        studyAtKoreaRepository.saveAndFlush(studyAtKorea);

        // Get all the studyAtKoreaList
        restStudyAtKoreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyAtKorea.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)));
    }

    @Test
    @Transactional
    void getStudyAtKorea() throws Exception {
        // Initialize the database
        studyAtKoreaRepository.saveAndFlush(studyAtKorea);

        // Get the studyAtKorea
        restStudyAtKoreaMockMvc
            .perform(get(ENTITY_API_URL_ID, studyAtKorea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studyAtKorea.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR));
    }

    @Test
    @Transactional
    void getNonExistingStudyAtKorea() throws Exception {
        // Get the studyAtKorea
        restStudyAtKoreaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudyAtKorea() throws Exception {
        // Initialize the database
        studyAtKoreaRepository.saveAndFlush(studyAtKorea);

        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();

        // Update the studyAtKorea
        StudyAtKorea updatedStudyAtKorea = studyAtKoreaRepository.findById(studyAtKorea.getId()).get();
        // Disconnect from session so that the updates on updatedStudyAtKorea are not directly saved in db
        em.detach(updatedStudyAtKorea);
        updatedStudyAtKorea
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR);

        restStudyAtKoreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudyAtKorea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStudyAtKorea))
            )
            .andExpect(status().isOk());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
        StudyAtKorea testStudyAtKorea = studyAtKoreaList.get(studyAtKoreaList.size() - 1);
        assertThat(testStudyAtKorea.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testStudyAtKorea.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testStudyAtKorea.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testStudyAtKorea.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testStudyAtKorea.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testStudyAtKorea.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
    }

    @Test
    @Transactional
    void putNonExistingStudyAtKorea() throws Exception {
        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();
        studyAtKorea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyAtKoreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studyAtKorea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studyAtKorea))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudyAtKorea() throws Exception {
        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();
        studyAtKorea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyAtKoreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studyAtKorea))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudyAtKorea() throws Exception {
        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();
        studyAtKorea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyAtKoreaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studyAtKorea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudyAtKoreaWithPatch() throws Exception {
        // Initialize the database
        studyAtKoreaRepository.saveAndFlush(studyAtKorea);

        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();

        // Update the studyAtKorea using partial update
        StudyAtKorea partialUpdatedStudyAtKorea = new StudyAtKorea();
        partialUpdatedStudyAtKorea.setId(studyAtKorea.getId());

        partialUpdatedStudyAtKorea.titleUz(UPDATED_TITLE_UZ).titleKr(UPDATED_TITLE_KR).contentRu(UPDATED_CONTENT_RU);

        restStudyAtKoreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudyAtKorea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudyAtKorea))
            )
            .andExpect(status().isOk());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
        StudyAtKorea testStudyAtKorea = studyAtKoreaList.get(studyAtKoreaList.size() - 1);
        assertThat(testStudyAtKorea.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testStudyAtKorea.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testStudyAtKorea.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testStudyAtKorea.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testStudyAtKorea.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testStudyAtKorea.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
    }

    @Test
    @Transactional
    void fullUpdateStudyAtKoreaWithPatch() throws Exception {
        // Initialize the database
        studyAtKoreaRepository.saveAndFlush(studyAtKorea);

        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();

        // Update the studyAtKorea using partial update
        StudyAtKorea partialUpdatedStudyAtKorea = new StudyAtKorea();
        partialUpdatedStudyAtKorea.setId(studyAtKorea.getId());

        partialUpdatedStudyAtKorea
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR);

        restStudyAtKoreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudyAtKorea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudyAtKorea))
            )
            .andExpect(status().isOk());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
        StudyAtKorea testStudyAtKorea = studyAtKoreaList.get(studyAtKoreaList.size() - 1);
        assertThat(testStudyAtKorea.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testStudyAtKorea.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testStudyAtKorea.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testStudyAtKorea.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testStudyAtKorea.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testStudyAtKorea.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
    }

    @Test
    @Transactional
    void patchNonExistingStudyAtKorea() throws Exception {
        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();
        studyAtKorea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyAtKoreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studyAtKorea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studyAtKorea))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudyAtKorea() throws Exception {
        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();
        studyAtKorea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyAtKoreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studyAtKorea))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudyAtKorea() throws Exception {
        int databaseSizeBeforeUpdate = studyAtKoreaRepository.findAll().size();
        studyAtKorea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyAtKoreaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(studyAtKorea))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudyAtKorea in the database
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudyAtKorea() throws Exception {
        // Initialize the database
        studyAtKoreaRepository.saveAndFlush(studyAtKorea);

        int databaseSizeBeforeDelete = studyAtKoreaRepository.findAll().size();

        // Delete the studyAtKorea
        restStudyAtKoreaMockMvc
            .perform(delete(ENTITY_API_URL_ID, studyAtKorea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudyAtKorea> studyAtKoreaList = studyAtKoreaRepository.findAll();
        assertThat(studyAtKoreaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
