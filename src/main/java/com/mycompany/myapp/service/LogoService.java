package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Logo;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Logo}.
 */
public interface LogoService {
    /**
     * Save a logo.
     *
     * @param logo the entity to save.
     * @return the persisted entity.
     */
    Logo save(Logo logo);

    /**
     * Updates a logo.
     *
     * @param logo the entity to update.
     * @return the persisted entity.
     */
    Logo update(Logo logo);

    /**
     * Partially updates a logo.
     *
     * @param logo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Logo> partialUpdate(Logo logo);

    /**
     * Get all the logos.
     *
     * @return the list of entities.
     */
    List<Logo> findAll();

    /**
     * Get the "id" logo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Logo> findOne(Long id);

    /**
     * Delete the "id" logo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
