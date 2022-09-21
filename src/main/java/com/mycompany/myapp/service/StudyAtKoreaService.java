package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.StudyAtKorea;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StudyAtKorea}.
 */
public interface StudyAtKoreaService {
    /**
     * Save a studyAtKorea.
     *
     * @param studyAtKorea the entity to save.
     * @return the persisted entity.
     */
    StudyAtKorea save(StudyAtKorea studyAtKorea);

    /**
     * Updates a studyAtKorea.
     *
     * @param studyAtKorea the entity to update.
     * @return the persisted entity.
     */
    StudyAtKorea update(StudyAtKorea studyAtKorea);

    /**
     * Partially updates a studyAtKorea.
     *
     * @param studyAtKorea the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StudyAtKorea> partialUpdate(StudyAtKorea studyAtKorea);

    /**
     * Get all the studyAtKoreas.
     *
     * @return the list of entities.
     */
    List<StudyAtKorea> findAll();

    /**
     * Get the "id" studyAtKorea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudyAtKorea> findOne(Long id);

    /**
     * Delete the "id" studyAtKorea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
