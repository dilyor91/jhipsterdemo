package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Logo;
import com.mycompany.myapp.repository.LogoRepository;
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
 * Integration tests for the {@link LogoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LogoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_DATA = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_DATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/logos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LogoRepository logoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogoMockMvc;

    private Logo logo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logo createEntity(EntityManager em) {
        Logo logo = new Logo().name(DEFAULT_NAME).logoData(DEFAULT_LOGO_DATA).status(DEFAULT_STATUS);
        return logo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logo createUpdatedEntity(EntityManager em) {
        Logo logo = new Logo().name(UPDATED_NAME).logoData(UPDATED_LOGO_DATA).status(UPDATED_STATUS);
        return logo;
    }

    @BeforeEach
    public void initTest() {
        logo = createEntity(em);
    }

    @Test
    @Transactional
    void createLogo() throws Exception {
        int databaseSizeBeforeCreate = logoRepository.findAll().size();
        // Create the Logo
        restLogoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isCreated());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeCreate + 1);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLogo.getLogoData()).isEqualTo(DEFAULT_LOGO_DATA);
        assertThat(testLogo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createLogoWithExistingId() throws Exception {
        // Create the Logo with an existing ID
        logo.setId(1L);

        int databaseSizeBeforeCreate = logoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLogos() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        // Get all the logoList
        restLogoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoData").value(hasItem(DEFAULT_LOGO_DATA)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        // Get the logo
        restLogoMockMvc
            .perform(get(ENTITY_API_URL_ID, logo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoData").value(DEFAULT_LOGO_DATA))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLogo() throws Exception {
        // Get the logo
        restLogoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Update the logo
        Logo updatedLogo = logoRepository.findById(logo.getId()).get();
        // Disconnect from session so that the updates on updatedLogo are not directly saved in db
        em.detach(updatedLogo);
        updatedLogo.name(UPDATED_NAME).logoData(UPDATED_LOGO_DATA).status(UPDATED_STATUS);

        restLogoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLogo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLogo))
            )
            .andExpect(status().isOk());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLogo.getLogoData()).isEqualTo(UPDATED_LOGO_DATA);
        assertThat(testLogo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();
        logo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();
        logo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();
        logo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLogoWithPatch() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Update the logo using partial update
        Logo partialUpdatedLogo = new Logo();
        partialUpdatedLogo.setId(logo.getId());

        partialUpdatedLogo.status(UPDATED_STATUS);

        restLogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogo))
            )
            .andExpect(status().isOk());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLogo.getLogoData()).isEqualTo(DEFAULT_LOGO_DATA);
        assertThat(testLogo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateLogoWithPatch() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Update the logo using partial update
        Logo partialUpdatedLogo = new Logo();
        partialUpdatedLogo.setId(logo.getId());

        partialUpdatedLogo.name(UPDATED_NAME).logoData(UPDATED_LOGO_DATA).status(UPDATED_STATUS);

        restLogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogo))
            )
            .andExpect(status().isOk());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLogo.getLogoData()).isEqualTo(UPDATED_LOGO_DATA);
        assertThat(testLogo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();
        logo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, logo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();
        logo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();
        logo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(logo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        int databaseSizeBeforeDelete = logoRepository.findAll().size();

        // Delete the logo
        restLogoMockMvc
            .perform(delete(ENTITY_API_URL_ID, logo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
