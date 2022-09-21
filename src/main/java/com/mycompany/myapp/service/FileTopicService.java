package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FileTopic;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FileTopic}.
 */
public interface FileTopicService {
    /**
     * Save a fileTopic.
     *
     * @param fileTopic the entity to save.
     * @return the persisted entity.
     */
    FileTopic save(FileTopic fileTopic);

    /**
     * Updates a fileTopic.
     *
     * @param fileTopic the entity to update.
     * @return the persisted entity.
     */
    FileTopic update(FileTopic fileTopic);

    /**
     * Partially updates a fileTopic.
     *
     * @param fileTopic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileTopic> partialUpdate(FileTopic fileTopic);

    /**
     * Get all the fileTopics.
     *
     * @return the list of entities.
     */
    List<FileTopic> findAll();

    /**
     * Get the "id" fileTopic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileTopic> findOne(Long id);

    /**
     * Delete the "id" fileTopic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
