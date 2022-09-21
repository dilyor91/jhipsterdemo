package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.News;
import com.mycompany.myapp.repository.NewsRepository;
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
 * Integration tests for the {@link NewsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsResourceIT {

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

    private static final LocalDate DEFAULT_POSTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/news";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsMockMvc;

    private News news;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createEntity(EntityManager em) {
        News news = new News()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .postedDate(DEFAULT_POSTED_DATE)
            .status(DEFAULT_STATUS);
        return news;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createUpdatedEntity(EntityManager em) {
        News news = new News()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE)
            .status(UPDATED_STATUS);
        return news;
    }

    @BeforeEach
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();
        // Create the News
        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testNews.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testNews.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testNews.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testNews.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testNews.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testNews.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testNews.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createNewsWithExistingId() throws Exception {
        // Create the News with an existing ID
        news.setId(1L);

        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc
            .perform(get(ENTITY_API_URL_ID, news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findById(news.getId()).get();
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE)
            .status(UPDATED_STATUS);

        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNews.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testNews.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testNews.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testNews.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testNews.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testNews.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testNews.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testNews.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, news.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(news))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(news))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsWithPatch() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news using partial update
        News partialUpdatedNews = new News();
        partialUpdatedNews.setId(news.getId());

        partialUpdatedNews
            .titleUz(UPDATED_TITLE_UZ)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE);

        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testNews.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testNews.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testNews.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testNews.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testNews.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testNews.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testNews.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNewsWithPatch() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news using partial update
        News partialUpdatedNews = new News();
        partialUpdatedNews.setId(news.getId());

        partialUpdatedNews
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .postedDate(UPDATED_POSTED_DATE)
            .status(UPDATED_STATUS);

        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testNews.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testNews.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testNews.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testNews.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testNews.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testNews.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testNews.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, news.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(news))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(news))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Delete the news
        restNewsMockMvc
            .perform(delete(ENTITY_API_URL_ID, news.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
