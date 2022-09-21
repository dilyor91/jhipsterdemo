package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Institution;
import com.mycompany.myapp.domain.enumeration.InstitutType;
import com.mycompany.myapp.repository.InstitutionRepository;
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
 * Integration tests for the {@link InstitutionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InstitutionResourceIT {

    private static final InstitutType DEFAULT_INSTITUTION_TYPE = InstitutType.COLLEGEANDLYCEUM;
    private static final InstitutType UPDATED_INSTITUTION_TYPE = InstitutType.KOREANUNIVERSITY;

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

    private static final String DEFAULT_LOGO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_DATA = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/institutions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstitutionMockMvc;

    private Institution institution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institution createEntity(EntityManager em) {
        Institution institution = new Institution()
            .institutionType(DEFAULT_INSTITUTION_TYPE)
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .logoName(DEFAULT_LOGO_NAME)
            .logoData(DEFAULT_LOGO_DATA);
        return institution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institution createUpdatedEntity(EntityManager em) {
        Institution institution = new Institution()
            .institutionType(UPDATED_INSTITUTION_TYPE)
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .logoName(UPDATED_LOGO_NAME)
            .logoData(UPDATED_LOGO_DATA);
        return institution;
    }

    @BeforeEach
    public void initTest() {
        institution = createEntity(em);
    }

    @Test
    @Transactional
    void createInstitution() throws Exception {
        int databaseSizeBeforeCreate = institutionRepository.findAll().size();
        // Create the Institution
        restInstitutionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(institution)))
            .andExpect(status().isCreated());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeCreate + 1);
        Institution testInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(testInstitution.getInstitutionType()).isEqualTo(DEFAULT_INSTITUTION_TYPE);
        assertThat(testInstitution.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testInstitution.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testInstitution.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testInstitution.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testInstitution.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testInstitution.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testInstitution.getLogoName()).isEqualTo(DEFAULT_LOGO_NAME);
        assertThat(testInstitution.getLogoData()).isEqualTo(DEFAULT_LOGO_DATA);
    }

    @Test
    @Transactional
    void createInstitutionWithExistingId() throws Exception {
        // Create the Institution with an existing ID
        institution.setId(1L);

        int databaseSizeBeforeCreate = institutionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstitutionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(institution)))
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInstitutions() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        // Get all the institutionList
        restInstitutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institution.getId().intValue())))
            .andExpect(jsonPath("$.[*].institutionType").value(hasItem(DEFAULT_INSTITUTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)))
            .andExpect(jsonPath("$.[*].logoName").value(hasItem(DEFAULT_LOGO_NAME)))
            .andExpect(jsonPath("$.[*].logoData").value(hasItem(DEFAULT_LOGO_DATA)));
    }

    @Test
    @Transactional
    void getInstitution() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        // Get the institution
        restInstitutionMockMvc
            .perform(get(ENTITY_API_URL_ID, institution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(institution.getId().intValue()))
            .andExpect(jsonPath("$.institutionType").value(DEFAULT_INSTITUTION_TYPE.toString()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR))
            .andExpect(jsonPath("$.logoName").value(DEFAULT_LOGO_NAME))
            .andExpect(jsonPath("$.logoData").value(DEFAULT_LOGO_DATA));
    }

    @Test
    @Transactional
    void getNonExistingInstitution() throws Exception {
        // Get the institution
        restInstitutionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInstitution() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

        // Update the institution
        Institution updatedInstitution = institutionRepository.findById(institution.getId()).get();
        // Disconnect from session so that the updates on updatedInstitution are not directly saved in db
        em.detach(updatedInstitution);
        updatedInstitution
            .institutionType(UPDATED_INSTITUTION_TYPE)
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .logoName(UPDATED_LOGO_NAME)
            .logoData(UPDATED_LOGO_DATA);

        restInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInstitution.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInstitution))
            )
            .andExpect(status().isOk());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
        Institution testInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(testInstitution.getInstitutionType()).isEqualTo(UPDATED_INSTITUTION_TYPE);
        assertThat(testInstitution.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testInstitution.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testInstitution.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testInstitution.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testInstitution.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testInstitution.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testInstitution.getLogoName()).isEqualTo(UPDATED_LOGO_NAME);
        assertThat(testInstitution.getLogoData()).isEqualTo(UPDATED_LOGO_DATA);
    }

    @Test
    @Transactional
    void putNonExistingInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
        institution.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, institution.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(institution))
            )
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
        institution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(institution))
            )
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
        institution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstitutionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(institution)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstitutionWithPatch() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

        // Update the institution using partial update
        Institution partialUpdatedInstitution = new Institution();
        partialUpdatedInstitution.setId(institution.getId());

        partialUpdatedInstitution
            .institutionType(UPDATED_INSTITUTION_TYPE)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .logoName(UPDATED_LOGO_NAME);

        restInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstitution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstitution))
            )
            .andExpect(status().isOk());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
        Institution testInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(testInstitution.getInstitutionType()).isEqualTo(UPDATED_INSTITUTION_TYPE);
        assertThat(testInstitution.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testInstitution.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testInstitution.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testInstitution.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testInstitution.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testInstitution.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testInstitution.getLogoName()).isEqualTo(UPDATED_LOGO_NAME);
        assertThat(testInstitution.getLogoData()).isEqualTo(DEFAULT_LOGO_DATA);
    }

    @Test
    @Transactional
    void fullUpdateInstitutionWithPatch() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

        // Update the institution using partial update
        Institution partialUpdatedInstitution = new Institution();
        partialUpdatedInstitution.setId(institution.getId());

        partialUpdatedInstitution
            .institutionType(UPDATED_INSTITUTION_TYPE)
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .logoName(UPDATED_LOGO_NAME)
            .logoData(UPDATED_LOGO_DATA);

        restInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstitution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstitution))
            )
            .andExpect(status().isOk());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
        Institution testInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(testInstitution.getInstitutionType()).isEqualTo(UPDATED_INSTITUTION_TYPE);
        assertThat(testInstitution.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testInstitution.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testInstitution.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testInstitution.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testInstitution.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testInstitution.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testInstitution.getLogoName()).isEqualTo(UPDATED_LOGO_NAME);
        assertThat(testInstitution.getLogoData()).isEqualTo(UPDATED_LOGO_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
        institution.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, institution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(institution))
            )
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
        institution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(institution))
            )
            .andExpect(status().isBadRequest());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstitution() throws Exception {
        int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
        institution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(institution))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Institution in the database
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstitution() throws Exception {
        // Initialize the database
        institutionRepository.saveAndFlush(institution);

        int databaseSizeBeforeDelete = institutionRepository.findAll().size();

        // Delete the institution
        restInstitutionMockMvc
            .perform(delete(ENTITY_API_URL_ID, institution.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Institution> institutionList = institutionRepository.findAll();
        assertThat(institutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
