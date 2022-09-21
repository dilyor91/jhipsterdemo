package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MaterialTopic;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MaterialTopic}.
 */
public interface MaterialTopicService {
    /**
     * Save a materialTopic.
     *
     * @param materialTopic the entity to save.
     * @return the persisted entity.
     */
    MaterialTopic save(MaterialTopic materialTopic);

    /**
     * Updates a materialTopic.
     *
     * @param materialTopic the entity to update.
     * @return the persisted entity.
     */
    MaterialTopic update(MaterialTopic materialTopic);

    /**
     * Partially updates a materialTopic.
     *
     * @param materialTopic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MaterialTopic> partialUpdate(MaterialTopic materialTopic);

    /**
     * Get all the materialTopics.
     *
     * @return the list of entities.
     */
    List<MaterialTopic> findAll();

    /**
     * Get the "id" materialTopic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialTopic> findOne(Long id);

    /**
     * Delete the "id" materialTopic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
