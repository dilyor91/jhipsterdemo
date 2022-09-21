package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CenterStructure;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CenterStructure}.
 */
public interface CenterStructureService {
    /**
     * Save a centerStructure.
     *
     * @param centerStructure the entity to save.
     * @return the persisted entity.
     */
    CenterStructure save(CenterStructure centerStructure);

    /**
     * Updates a centerStructure.
     *
     * @param centerStructure the entity to update.
     * @return the persisted entity.
     */
    CenterStructure update(CenterStructure centerStructure);

    /**
     * Partially updates a centerStructure.
     *
     * @param centerStructure the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CenterStructure> partialUpdate(CenterStructure centerStructure);

    /**
     * Get all the centerStructures.
     *
     * @return the list of entities.
     */
    List<CenterStructure> findAll();

    /**
     * Get the "id" centerStructure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterStructure> findOne(Long id);

    /**
     * Delete the "id" centerStructure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
