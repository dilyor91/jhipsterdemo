package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OurHistory;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OurHistory}.
 */
public interface OurHistoryService {
    /**
     * Save a ourHistory.
     *
     * @param ourHistory the entity to save.
     * @return the persisted entity.
     */
    OurHistory save(OurHistory ourHistory);

    /**
     * Updates a ourHistory.
     *
     * @param ourHistory the entity to update.
     * @return the persisted entity.
     */
    OurHistory update(OurHistory ourHistory);

    /**
     * Partially updates a ourHistory.
     *
     * @param ourHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OurHistory> partialUpdate(OurHistory ourHistory);

    /**
     * Get all the ourHistories.
     *
     * @return the list of entities.
     */
    List<OurHistory> findAll();

    /**
     * Get the "id" ourHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OurHistory> findOne(Long id);

    /**
     * Delete the "id" ourHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
