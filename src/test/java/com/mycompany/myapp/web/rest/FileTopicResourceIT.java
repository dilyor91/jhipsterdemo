package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FileTopic;
import com.mycompany.myapp.repository.FileTopicRepository;
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
 * Integration tests for the {@link FileTopicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileTopicResourceIT {

    private static final String DEFAULT_FILE_ORGINAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ORGINAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME_UZ = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME_KR = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME_KR = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/file-topics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileTopicRepository fileTopicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileTopicMockMvc;

    private FileTopic fileTopic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileTopic createEntity(EntityManager em) {
        FileTopic fileTopic = new FileTopic()
            .fileOrginalName(DEFAULT_FILE_ORGINAL_NAME)
            .fileNameUz(DEFAULT_FILE_NAME_UZ)
            .fileNameRu(DEFAULT_FILE_NAME_RU)
            .fileNameKr(DEFAULT_FILE_NAME_KR)
            .fileType(DEFAULT_FILE_TYPE)
            .fileSize(DEFAULT_FILE_SIZE)
            .filePath(DEFAULT_FILE_PATH);
        return fileTopic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileTopic createUpdatedEntity(EntityManager em) {
        FileTopic fileTopic = new FileTopic()
            .fileOrginalName(UPDATED_FILE_ORGINAL_NAME)
            .fileNameUz(UPDATED_FILE_NAME_UZ)
            .fileNameRu(UPDATED_FILE_NAME_RU)
            .fileNameKr(UPDATED_FILE_NAME_KR)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .filePath(UPDATED_FILE_PATH);
        return fileTopic;
    }

    @BeforeEach
    public void initTest() {
        fileTopic = createEntity(em);
    }

    @Test
    @Transactional
    void createFileTopic() throws Exception {
        int databaseSizeBeforeCreate = fileTopicRepository.findAll().size();
        // Create the FileTopic
        restFileTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileTopic)))
            .andExpect(status().isCreated());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeCreate + 1);
        FileTopic testFileTopic = fileTopicList.get(fileTopicList.size() - 1);
        assertThat(testFileTopic.getFileOrginalName()).isEqualTo(DEFAULT_FILE_ORGINAL_NAME);
        assertThat(testFileTopic.getFileNameUz()).isEqualTo(DEFAULT_FILE_NAME_UZ);
        assertThat(testFileTopic.getFileNameRu()).isEqualTo(DEFAULT_FILE_NAME_RU);
        assertThat(testFileTopic.getFileNameKr()).isEqualTo(DEFAULT_FILE_NAME_KR);
        assertThat(testFileTopic.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileTopic.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testFileTopic.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    void createFileTopicWithExistingId() throws Exception {
        // Create the FileTopic with an existing ID
        fileTopic.setId(1L);

        int databaseSizeBeforeCreate = fileTopicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileTopic)))
            .andExpect(status().isBadRequest());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFileTopics() throws Exception {
        // Initialize the database
        fileTopicRepository.saveAndFlush(fileTopic);

        // Get all the fileTopicList
        restFileTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileTopic.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileOrginalName").value(hasItem(DEFAULT_FILE_ORGINAL_NAME)))
            .andExpect(jsonPath("$.[*].fileNameUz").value(hasItem(DEFAULT_FILE_NAME_UZ)))
            .andExpect(jsonPath("$.[*].fileNameRu").value(hasItem(DEFAULT_FILE_NAME_RU)))
            .andExpect(jsonPath("$.[*].fileNameKr").value(hasItem(DEFAULT_FILE_NAME_KR)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)));
    }

    @Test
    @Transactional
    void getFileTopic() throws Exception {
        // Initialize the database
        fileTopicRepository.saveAndFlush(fileTopic);

        // Get the fileTopic
        restFileTopicMockMvc
            .perform(get(ENTITY_API_URL_ID, fileTopic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileTopic.getId().intValue()))
            .andExpect(jsonPath("$.fileOrginalName").value(DEFAULT_FILE_ORGINAL_NAME))
            .andExpect(jsonPath("$.fileNameUz").value(DEFAULT_FILE_NAME_UZ))
            .andExpect(jsonPath("$.fileNameRu").value(DEFAULT_FILE_NAME_RU))
            .andExpect(jsonPath("$.fileNameKr").value(DEFAULT_FILE_NAME_KR))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFileTopic() throws Exception {
        // Get the fileTopic
        restFileTopicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFileTopic() throws Exception {
        // Initialize the database
        fileTopicRepository.saveAndFlush(fileTopic);

        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();

        // Update the fileTopic
        FileTopic updatedFileTopic = fileTopicRepository.findById(fileTopic.getId()).get();
        // Disconnect from session so that the updates on updatedFileTopic are not directly saved in db
        em.detach(updatedFileTopic);
        updatedFileTopic
            .fileOrginalName(UPDATED_FILE_ORGINAL_NAME)
            .fileNameUz(UPDATED_FILE_NAME_UZ)
            .fileNameRu(UPDATED_FILE_NAME_RU)
            .fileNameKr(UPDATED_FILE_NAME_KR)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .filePath(UPDATED_FILE_PATH);

        restFileTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFileTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFileTopic))
            )
            .andExpect(status().isOk());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
        FileTopic testFileTopic = fileTopicList.get(fileTopicList.size() - 1);
        assertThat(testFileTopic.getFileOrginalName()).isEqualTo(UPDATED_FILE_ORGINAL_NAME);
        assertThat(testFileTopic.getFileNameUz()).isEqualTo(UPDATED_FILE_NAME_UZ);
        assertThat(testFileTopic.getFileNameRu()).isEqualTo(UPDATED_FILE_NAME_RU);
        assertThat(testFileTopic.getFileNameKr()).isEqualTo(UPDATED_FILE_NAME_KR);
        assertThat(testFileTopic.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileTopic.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testFileTopic.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void putNonExistingFileTopic() throws Exception {
        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();
        fileTopic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileTopic() throws Exception {
        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();
        fileTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileTopic() throws Exception {
        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();
        fileTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileTopicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileTopic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileTopicWithPatch() throws Exception {
        // Initialize the database
        fileTopicRepository.saveAndFlush(fileTopic);

        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();

        // Update the fileTopic using partial update
        FileTopic partialUpdatedFileTopic = new FileTopic();
        partialUpdatedFileTopic.setId(fileTopic.getId());

        partialUpdatedFileTopic.filePath(UPDATED_FILE_PATH);

        restFileTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileTopic))
            )
            .andExpect(status().isOk());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
        FileTopic testFileTopic = fileTopicList.get(fileTopicList.size() - 1);
        assertThat(testFileTopic.getFileOrginalName()).isEqualTo(DEFAULT_FILE_ORGINAL_NAME);
        assertThat(testFileTopic.getFileNameUz()).isEqualTo(DEFAULT_FILE_NAME_UZ);
        assertThat(testFileTopic.getFileNameRu()).isEqualTo(DEFAULT_FILE_NAME_RU);
        assertThat(testFileTopic.getFileNameKr()).isEqualTo(DEFAULT_FILE_NAME_KR);
        assertThat(testFileTopic.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileTopic.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testFileTopic.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void fullUpdateFileTopicWithPatch() throws Exception {
        // Initialize the database
        fileTopicRepository.saveAndFlush(fileTopic);

        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();

        // Update the fileTopic using partial update
        FileTopic partialUpdatedFileTopic = new FileTopic();
        partialUpdatedFileTopic.setId(fileTopic.getId());

        partialUpdatedFileTopic
            .fileOrginalName(UPDATED_FILE_ORGINAL_NAME)
            .fileNameUz(UPDATED_FILE_NAME_UZ)
            .fileNameRu(UPDATED_FILE_NAME_RU)
            .fileNameKr(UPDATED_FILE_NAME_KR)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .filePath(UPDATED_FILE_PATH);

        restFileTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileTopic))
            )
            .andExpect(status().isOk());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
        FileTopic testFileTopic = fileTopicList.get(fileTopicList.size() - 1);
        assertThat(testFileTopic.getFileOrginalName()).isEqualTo(UPDATED_FILE_ORGINAL_NAME);
        assertThat(testFileTopic.getFileNameUz()).isEqualTo(UPDATED_FILE_NAME_UZ);
        assertThat(testFileTopic.getFileNameRu()).isEqualTo(UPDATED_FILE_NAME_RU);
        assertThat(testFileTopic.getFileNameKr()).isEqualTo(UPDATED_FILE_NAME_KR);
        assertThat(testFileTopic.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileTopic.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testFileTopic.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingFileTopic() throws Exception {
        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();
        fileTopic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileTopic() throws Exception {
        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();
        fileTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileTopic() throws Exception {
        int databaseSizeBeforeUpdate = fileTopicRepository.findAll().size();
        fileTopic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileTopicMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileTopic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileTopic in the database
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileTopic() throws Exception {
        // Initialize the database
        fileTopicRepository.saveAndFlush(fileTopic);

        int databaseSizeBeforeDelete = fileTopicRepository.findAll().size();

        // Delete the fileTopic
        restFileTopicMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileTopic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileTopic> fileTopicList = fileTopicRepository.findAll();
        assertThat(fileTopicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
