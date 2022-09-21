package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MaterialTopicLevel;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MaterialTopicLevel}.
 */
public interface MaterialTopicLevelService {
    /**
     * Save a materialTopicLevel.
     *
     * @param materialTopicLevel the entity to save.
     * @return the persisted entity.
     */
    MaterialTopicLevel save(MaterialTopicLevel materialTopicLevel);

    /**
     * Updates a materialTopicLevel.
     *
     * @param materialTopicLevel the entity to update.
     * @return the persisted entity.
     */
    MaterialTopicLevel update(MaterialTopicLevel materialTopicLevel);

    /**
     * Partially updates a materialTopicLevel.
     *
     * @param materialTopicLevel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MaterialTopicLevel> partialUpdate(MaterialTopicLevel materialTopicLevel);

    /**
     * Get all the materialTopicLevels.
     *
     * @return the list of entities.
     */
    List<MaterialTopicLevel> findAll();

    /**
     * Get the "id" materialTopicLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialTopicLevel> findOne(Long id);

    /**
     * Delete the "id" materialTopicLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
