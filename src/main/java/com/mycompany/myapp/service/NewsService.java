package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.News;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link News}.
 */
public interface NewsService {
    /**
     * Save a news.
     *
     * @param news the entity to save.
     * @return the persisted entity.
     */
    News save(News news);

    /**
     * Updates a news.
     *
     * @param news the entity to update.
     * @return the persisted entity.
     */
    News update(News news);

    /**
     * Partially updates a news.
     *
     * @param news the entity to update partially.
     * @return the persisted entity.
     */
    Optional<News> partialUpdate(News news);

    /**
     * Get all the news.
     *
     * @return the list of entities.
     */
    List<News> findAll();

    /**
     * Get the "id" news.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<News> findOne(Long id);

    /**
     * Delete the "id" news.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
