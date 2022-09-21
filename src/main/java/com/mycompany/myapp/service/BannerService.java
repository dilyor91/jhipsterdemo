package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Banner;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Banner}.
 */
public interface BannerService {
    /**
     * Save a banner.
     *
     * @param banner the entity to save.
     * @return the persisted entity.
     */
    Banner save(Banner banner);

    /**
     * Updates a banner.
     *
     * @param banner the entity to update.
     * @return the persisted entity.
     */
    Banner update(Banner banner);

    /**
     * Partially updates a banner.
     *
     * @param banner the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Banner> partialUpdate(Banner banner);

    /**
     * Get all the banners.
     *
     * @return the list of entities.
     */
    List<Banner> findAll();

    /**
     * Get the "id" banner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Banner> findOne(Long id);

    /**
     * Delete the "id" banner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
