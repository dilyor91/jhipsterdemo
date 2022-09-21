package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Banner;
import com.mycompany.myapp.repository.BannerRepository;
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
 * Integration tests for the {@link BannerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BannerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_DATA = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_DATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/banners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannerMockMvc;

    private Banner banner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createEntity(EntityManager em) {
        Banner banner = new Banner().name(DEFAULT_NAME).bannerData(DEFAULT_BANNER_DATA).status(DEFAULT_STATUS);
        return banner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createUpdatedEntity(EntityManager em) {
        Banner banner = new Banner().name(UPDATED_NAME).bannerData(UPDATED_BANNER_DATA).status(UPDATED_STATUS);
        return banner;
    }

    @BeforeEach
    public void initTest() {
        banner = createEntity(em);
    }

    @Test
    @Transactional
    void createBanner() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();
        // Create the Banner
        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBanner.getBannerData()).isEqualTo(DEFAULT_BANNER_DATA);
        assertThat(testBanner.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createBannerWithExistingId() throws Exception {
        // Create the Banner with an existing ID
        banner.setId(1L);

        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanners() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList
        restBannerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bannerData").value(hasItem(DEFAULT_BANNER_DATA)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get the banner
        restBannerMockMvc
            .perform(get(ENTITY_API_URL_ID, banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bannerData").value(DEFAULT_BANNER_DATA))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBanner() throws Exception {
        // Get the banner
        restBannerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner
        Banner updatedBanner = bannerRepository.findById(banner.getId()).get();
        // Disconnect from session so that the updates on updatedBanner are not directly saved in db
        em.detach(updatedBanner);
        updatedBanner.name(UPDATED_NAME).bannerData(UPDATED_BANNER_DATA).status(UPDATED_STATUS);

        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBanner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.getBannerData()).isEqualTo(UPDATED_BANNER_DATA);
        assertThat(testBanner.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBannerWithPatch() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner using partial update
        Banner partialUpdatedBanner = new Banner();
        partialUpdatedBanner.setId(banner.getId());

        partialUpdatedBanner.name(UPDATED_NAME).bannerData(UPDATED_BANNER_DATA).status(UPDATED_STATUS);

        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.getBannerData()).isEqualTo(UPDATED_BANNER_DATA);
        assertThat(testBanner.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateBannerWithPatch() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner using partial update
        Banner partialUpdatedBanner = new Banner();
        partialUpdatedBanner.setId(banner.getId());

        partialUpdatedBanner.name(UPDATED_NAME).bannerData(UPDATED_BANNER_DATA).status(UPDATED_STATUS);

        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanner))
            )
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.getBannerData()).isEqualTo(UPDATED_BANNER_DATA);
        assertThat(testBanner.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
        banner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeDelete = bannerRepository.findAll().size();

        // Delete the banner
        restBannerMockMvc
            .perform(delete(ENTITY_API_URL_ID, banner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
