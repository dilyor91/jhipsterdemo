package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.WorkPlan;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorkPlan}.
 */
public interface WorkPlanService {
    /**
     * Save a workPlan.
     *
     * @param workPlan the entity to save.
     * @return the persisted entity.
     */
    WorkPlan save(WorkPlan workPlan);

    /**
     * Updates a workPlan.
     *
     * @param workPlan the entity to update.
     * @return the persisted entity.
     */
    WorkPlan update(WorkPlan workPlan);

    /**
     * Partially updates a workPlan.
     *
     * @param workPlan the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkPlan> partialUpdate(WorkPlan workPlan);

    /**
     * Get all the workPlans.
     *
     * @return the list of entities.
     */
    List<WorkPlan> findAll();

    /**
     * Get the "id" workPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkPlan> findOne(Long id);

    /**
     * Delete the "id" workPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
