package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.WorkPlan;
import com.mycompany.myapp.domain.enumeration.PlanType;
import com.mycompany.myapp.repository.WorkPlanRepository;
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
 * Integration tests for the {@link WorkPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkPlanResourceIT {

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

    private static final PlanType DEFAULT_PLAN_TYPE = PlanType.MONTH;
    private static final PlanType UPDATED_PLAN_TYPE = PlanType.YEAR;

    private static final String ENTITY_API_URL = "/api/work-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkPlanRepository workPlanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkPlanMockMvc;

    private WorkPlan workPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkPlan createEntity(EntityManager em) {
        WorkPlan workPlan = new WorkPlan()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .planType(DEFAULT_PLAN_TYPE);
        return workPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkPlan createUpdatedEntity(EntityManager em) {
        WorkPlan workPlan = new WorkPlan()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .planType(UPDATED_PLAN_TYPE);
        return workPlan;
    }

    @BeforeEach
    public void initTest() {
        workPlan = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkPlan() throws Exception {
        int databaseSizeBeforeCreate = workPlanRepository.findAll().size();
        // Create the WorkPlan
        restWorkPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workPlan)))
            .andExpect(status().isCreated());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeCreate + 1);
        WorkPlan testWorkPlan = workPlanList.get(workPlanList.size() - 1);
        assertThat(testWorkPlan.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testWorkPlan.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testWorkPlan.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testWorkPlan.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testWorkPlan.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testWorkPlan.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testWorkPlan.getPlanType()).isEqualTo(DEFAULT_PLAN_TYPE);
    }

    @Test
    @Transactional
    void createWorkPlanWithExistingId() throws Exception {
        // Create the WorkPlan with an existing ID
        workPlan.setId(1L);

        int databaseSizeBeforeCreate = workPlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workPlan)))
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkPlans() throws Exception {
        // Initialize the database
        workPlanRepository.saveAndFlush(workPlan);

        // Get all the workPlanList
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)))
            .andExpect(jsonPath("$.[*].planType").value(hasItem(DEFAULT_PLAN_TYPE.toString())));
    }

    @Test
    @Transactional
    void getWorkPlan() throws Exception {
        // Initialize the database
        workPlanRepository.saveAndFlush(workPlan);

        // Get the workPlan
        restWorkPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, workPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workPlan.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR))
            .andExpect(jsonPath("$.planType").value(DEFAULT_PLAN_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWorkPlan() throws Exception {
        // Get the workPlan
        restWorkPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkPlan() throws Exception {
        // Initialize the database
        workPlanRepository.saveAndFlush(workPlan);

        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();

        // Update the workPlan
        WorkPlan updatedWorkPlan = workPlanRepository.findById(workPlan.getId()).get();
        // Disconnect from session so that the updates on updatedWorkPlan are not directly saved in db
        em.detach(updatedWorkPlan);
        updatedWorkPlan
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .planType(UPDATED_PLAN_TYPE);

        restWorkPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkPlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkPlan))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
        WorkPlan testWorkPlan = workPlanList.get(workPlanList.size() - 1);
        assertThat(testWorkPlan.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testWorkPlan.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testWorkPlan.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testWorkPlan.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testWorkPlan.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testWorkPlan.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testWorkPlan.getPlanType()).isEqualTo(UPDATED_PLAN_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingWorkPlan() throws Exception {
        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();
        workPlan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workPlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkPlan() throws Exception {
        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();
        workPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkPlan() throws Exception {
        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();
        workPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workPlan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkPlanWithPatch() throws Exception {
        // Initialize the database
        workPlanRepository.saveAndFlush(workPlan);

        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();

        // Update the workPlan using partial update
        WorkPlan partialUpdatedWorkPlan = new WorkPlan();
        partialUpdatedWorkPlan.setId(workPlan.getId());

        partialUpdatedWorkPlan
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentKr(UPDATED_CONTENT_KR)
            .planType(UPDATED_PLAN_TYPE);

        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkPlan))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
        WorkPlan testWorkPlan = workPlanList.get(workPlanList.size() - 1);
        assertThat(testWorkPlan.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testWorkPlan.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testWorkPlan.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testWorkPlan.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testWorkPlan.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testWorkPlan.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testWorkPlan.getPlanType()).isEqualTo(UPDATED_PLAN_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateWorkPlanWithPatch() throws Exception {
        // Initialize the database
        workPlanRepository.saveAndFlush(workPlan);

        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();

        // Update the workPlan using partial update
        WorkPlan partialUpdatedWorkPlan = new WorkPlan();
        partialUpdatedWorkPlan.setId(workPlan.getId());

        partialUpdatedWorkPlan
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .planType(UPDATED_PLAN_TYPE);

        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkPlan))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
        WorkPlan testWorkPlan = workPlanList.get(workPlanList.size() - 1);
        assertThat(testWorkPlan.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testWorkPlan.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testWorkPlan.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testWorkPlan.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testWorkPlan.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testWorkPlan.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testWorkPlan.getPlanType()).isEqualTo(UPDATED_PLAN_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingWorkPlan() throws Exception {
        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();
        workPlan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkPlan() throws Exception {
        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();
        workPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workPlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkPlan() throws Exception {
        int databaseSizeBeforeUpdate = workPlanRepository.findAll().size();
        workPlan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workPlan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkPlan in the database
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkPlan() throws Exception {
        // Initialize the database
        workPlanRepository.saveAndFlush(workPlan);

        int databaseSizeBeforeDelete = workPlanRepository.findAll().size();

        // Delete the workPlan
        restWorkPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, workPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkPlan> workPlanList = workPlanRepository.findAll();
        assertThat(workPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
