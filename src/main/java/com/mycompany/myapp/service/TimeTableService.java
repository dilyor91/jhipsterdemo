package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TimeTable;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TimeTable}.
 */
public interface TimeTableService {
    /**
     * Save a timeTable.
     *
     * @param timeTable the entity to save.
     * @return the persisted entity.
     */
    TimeTable save(TimeTable timeTable);

    /**
     * Updates a timeTable.
     *
     * @param timeTable the entity to update.
     * @return the persisted entity.
     */
    TimeTable update(TimeTable timeTable);

    /**
     * Partially updates a timeTable.
     *
     * @param timeTable the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimeTable> partialUpdate(TimeTable timeTable);

    /**
     * Get all the timeTables.
     *
     * @return the list of entities.
     */
    List<TimeTable> findAll();

    /**
     * Get the "id" timeTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeTable> findOne(Long id);

    /**
     * Delete the "id" timeTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
