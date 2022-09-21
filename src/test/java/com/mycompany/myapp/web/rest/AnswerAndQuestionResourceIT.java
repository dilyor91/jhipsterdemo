package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AnswerAndQuestion;
import com.mycompany.myapp.repository.AnswerAndQuestionRepository;
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
 * Integration tests for the {@link AnswerAndQuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnswerAndQuestionResourceIT {

    private static final String DEFAULT_QUESTION_UZ = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_RU = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_RU = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_KR = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_KR = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER_UZ = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER_RU = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_RU = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER_KR = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_KR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/answer-and-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnswerAndQuestionRepository answerAndQuestionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerAndQuestionMockMvc;

    private AnswerAndQuestion answerAndQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerAndQuestion createEntity(EntityManager em) {
        AnswerAndQuestion answerAndQuestion = new AnswerAndQuestion()
            .questionUz(DEFAULT_QUESTION_UZ)
            .questionRu(DEFAULT_QUESTION_RU)
            .questionKr(DEFAULT_QUESTION_KR)
            .answerUz(DEFAULT_ANSWER_UZ)
            .answerRu(DEFAULT_ANSWER_RU)
            .answerKr(DEFAULT_ANSWER_KR);
        return answerAndQuestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerAndQuestion createUpdatedEntity(EntityManager em) {
        AnswerAndQuestion answerAndQuestion = new AnswerAndQuestion()
            .questionUz(UPDATED_QUESTION_UZ)
            .questionRu(UPDATED_QUESTION_RU)
            .questionKr(UPDATED_QUESTION_KR)
            .answerUz(UPDATED_ANSWER_UZ)
            .answerRu(UPDATED_ANSWER_RU)
            .answerKr(UPDATED_ANSWER_KR);
        return answerAndQuestion;
    }

    @BeforeEach
    public void initTest() {
        answerAndQuestion = createEntity(em);
    }

    @Test
    @Transactional
    void createAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeCreate = answerAndQuestionRepository.findAll().size();
        // Create the AnswerAndQuestion
        restAnswerAndQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isCreated());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        AnswerAndQuestion testAnswerAndQuestion = answerAndQuestionList.get(answerAndQuestionList.size() - 1);
        assertThat(testAnswerAndQuestion.getQuestionUz()).isEqualTo(DEFAULT_QUESTION_UZ);
        assertThat(testAnswerAndQuestion.getQuestionRu()).isEqualTo(DEFAULT_QUESTION_RU);
        assertThat(testAnswerAndQuestion.getQuestionKr()).isEqualTo(DEFAULT_QUESTION_KR);
        assertThat(testAnswerAndQuestion.getAnswerUz()).isEqualTo(DEFAULT_ANSWER_UZ);
        assertThat(testAnswerAndQuestion.getAnswerRu()).isEqualTo(DEFAULT_ANSWER_RU);
        assertThat(testAnswerAndQuestion.getAnswerKr()).isEqualTo(DEFAULT_ANSWER_KR);
    }

    @Test
    @Transactional
    void createAnswerAndQuestionWithExistingId() throws Exception {
        // Create the AnswerAndQuestion with an existing ID
        answerAndQuestion.setId(1L);

        int databaseSizeBeforeCreate = answerAndQuestionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerAndQuestionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnswerAndQuestions() throws Exception {
        // Initialize the database
        answerAndQuestionRepository.saveAndFlush(answerAndQuestion);

        // Get all the answerAndQuestionList
        restAnswerAndQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerAndQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionUz").value(hasItem(DEFAULT_QUESTION_UZ)))
            .andExpect(jsonPath("$.[*].questionRu").value(hasItem(DEFAULT_QUESTION_RU)))
            .andExpect(jsonPath("$.[*].questionKr").value(hasItem(DEFAULT_QUESTION_KR)))
            .andExpect(jsonPath("$.[*].answerUz").value(hasItem(DEFAULT_ANSWER_UZ)))
            .andExpect(jsonPath("$.[*].answerRu").value(hasItem(DEFAULT_ANSWER_RU)))
            .andExpect(jsonPath("$.[*].answerKr").value(hasItem(DEFAULT_ANSWER_KR)));
    }

    @Test
    @Transactional
    void getAnswerAndQuestion() throws Exception {
        // Initialize the database
        answerAndQuestionRepository.saveAndFlush(answerAndQuestion);

        // Get the answerAndQuestion
        restAnswerAndQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, answerAndQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answerAndQuestion.getId().intValue()))
            .andExpect(jsonPath("$.questionUz").value(DEFAULT_QUESTION_UZ))
            .andExpect(jsonPath("$.questionRu").value(DEFAULT_QUESTION_RU))
            .andExpect(jsonPath("$.questionKr").value(DEFAULT_QUESTION_KR))
            .andExpect(jsonPath("$.answerUz").value(DEFAULT_ANSWER_UZ))
            .andExpect(jsonPath("$.answerRu").value(DEFAULT_ANSWER_RU))
            .andExpect(jsonPath("$.answerKr").value(DEFAULT_ANSWER_KR));
    }

    @Test
    @Transactional
    void getNonExistingAnswerAndQuestion() throws Exception {
        // Get the answerAndQuestion
        restAnswerAndQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnswerAndQuestion() throws Exception {
        // Initialize the database
        answerAndQuestionRepository.saveAndFlush(answerAndQuestion);

        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();

        // Update the answerAndQuestion
        AnswerAndQuestion updatedAnswerAndQuestion = answerAndQuestionRepository.findById(answerAndQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedAnswerAndQuestion are not directly saved in db
        em.detach(updatedAnswerAndQuestion);
        updatedAnswerAndQuestion
            .questionUz(UPDATED_QUESTION_UZ)
            .questionRu(UPDATED_QUESTION_RU)
            .questionKr(UPDATED_QUESTION_KR)
            .answerUz(UPDATED_ANSWER_UZ)
            .answerRu(UPDATED_ANSWER_RU)
            .answerKr(UPDATED_ANSWER_KR);

        restAnswerAndQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnswerAndQuestion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnswerAndQuestion))
            )
            .andExpect(status().isOk());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
        AnswerAndQuestion testAnswerAndQuestion = answerAndQuestionList.get(answerAndQuestionList.size() - 1);
        assertThat(testAnswerAndQuestion.getQuestionUz()).isEqualTo(UPDATED_QUESTION_UZ);
        assertThat(testAnswerAndQuestion.getQuestionRu()).isEqualTo(UPDATED_QUESTION_RU);
        assertThat(testAnswerAndQuestion.getQuestionKr()).isEqualTo(UPDATED_QUESTION_KR);
        assertThat(testAnswerAndQuestion.getAnswerUz()).isEqualTo(UPDATED_ANSWER_UZ);
        assertThat(testAnswerAndQuestion.getAnswerRu()).isEqualTo(UPDATED_ANSWER_RU);
        assertThat(testAnswerAndQuestion.getAnswerKr()).isEqualTo(UPDATED_ANSWER_KR);
    }

    @Test
    @Transactional
    void putNonExistingAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();
        answerAndQuestion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerAndQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, answerAndQuestion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();
        answerAndQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerAndQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();
        answerAndQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerAndQuestionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnswerAndQuestionWithPatch() throws Exception {
        // Initialize the database
        answerAndQuestionRepository.saveAndFlush(answerAndQuestion);

        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();

        // Update the answerAndQuestion using partial update
        AnswerAndQuestion partialUpdatedAnswerAndQuestion = new AnswerAndQuestion();
        partialUpdatedAnswerAndQuestion.setId(answerAndQuestion.getId());

        partialUpdatedAnswerAndQuestion.questionUz(UPDATED_QUESTION_UZ).answerUz(UPDATED_ANSWER_UZ).answerRu(UPDATED_ANSWER_RU);

        restAnswerAndQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswerAndQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnswerAndQuestion))
            )
            .andExpect(status().isOk());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
        AnswerAndQuestion testAnswerAndQuestion = answerAndQuestionList.get(answerAndQuestionList.size() - 1);
        assertThat(testAnswerAndQuestion.getQuestionUz()).isEqualTo(UPDATED_QUESTION_UZ);
        assertThat(testAnswerAndQuestion.getQuestionRu()).isEqualTo(DEFAULT_QUESTION_RU);
        assertThat(testAnswerAndQuestion.getQuestionKr()).isEqualTo(DEFAULT_QUESTION_KR);
        assertThat(testAnswerAndQuestion.getAnswerUz()).isEqualTo(UPDATED_ANSWER_UZ);
        assertThat(testAnswerAndQuestion.getAnswerRu()).isEqualTo(UPDATED_ANSWER_RU);
        assertThat(testAnswerAndQuestion.getAnswerKr()).isEqualTo(DEFAULT_ANSWER_KR);
    }

    @Test
    @Transactional
    void fullUpdateAnswerAndQuestionWithPatch() throws Exception {
        // Initialize the database
        answerAndQuestionRepository.saveAndFlush(answerAndQuestion);

        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();

        // Update the answerAndQuestion using partial update
        AnswerAndQuestion partialUpdatedAnswerAndQuestion = new AnswerAndQuestion();
        partialUpdatedAnswerAndQuestion.setId(answerAndQuestion.getId());

        partialUpdatedAnswerAndQuestion
            .questionUz(UPDATED_QUESTION_UZ)
            .questionRu(UPDATED_QUESTION_RU)
            .questionKr(UPDATED_QUESTION_KR)
            .answerUz(UPDATED_ANSWER_UZ)
            .answerRu(UPDATED_ANSWER_RU)
            .answerKr(UPDATED_ANSWER_KR);

        restAnswerAndQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswerAndQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnswerAndQuestion))
            )
            .andExpect(status().isOk());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
        AnswerAndQuestion testAnswerAndQuestion = answerAndQuestionList.get(answerAndQuestionList.size() - 1);
        assertThat(testAnswerAndQuestion.getQuestionUz()).isEqualTo(UPDATED_QUESTION_UZ);
        assertThat(testAnswerAndQuestion.getQuestionRu()).isEqualTo(UPDATED_QUESTION_RU);
        assertThat(testAnswerAndQuestion.getQuestionKr()).isEqualTo(UPDATED_QUESTION_KR);
        assertThat(testAnswerAndQuestion.getAnswerUz()).isEqualTo(UPDATED_ANSWER_UZ);
        assertThat(testAnswerAndQuestion.getAnswerRu()).isEqualTo(UPDATED_ANSWER_RU);
        assertThat(testAnswerAndQuestion.getAnswerKr()).isEqualTo(UPDATED_ANSWER_KR);
    }

    @Test
    @Transactional
    void patchNonExistingAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();
        answerAndQuestion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerAndQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, answerAndQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();
        answerAndQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerAndQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnswerAndQuestion() throws Exception {
        int databaseSizeBeforeUpdate = answerAndQuestionRepository.findAll().size();
        answerAndQuestion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerAndQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerAndQuestion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnswerAndQuestion in the database
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnswerAndQuestion() throws Exception {
        // Initialize the database
        answerAndQuestionRepository.saveAndFlush(answerAndQuestion);

        int databaseSizeBeforeDelete = answerAndQuestionRepository.findAll().size();

        // Delete the answerAndQuestion
        restAnswerAndQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, answerAndQuestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnswerAndQuestion> answerAndQuestionList = answerAndQuestionRepository.findAll();
        assertThat(answerAndQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
