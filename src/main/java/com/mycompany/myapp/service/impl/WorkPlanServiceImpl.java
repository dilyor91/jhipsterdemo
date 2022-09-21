package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.WorkPlan;
import com.mycompany.myapp.repository.WorkPlanRepository;
import com.mycompany.myapp.service.WorkPlanService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkPlan}.
 */
@Service
@Transactional
public class WorkPlanServiceImpl implements WorkPlanService {

    private final Logger log = LoggerFactory.getLogger(WorkPlanServiceImpl.class);

    private final WorkPlanRepository workPlanRepository;

    public WorkPlanServiceImpl(WorkPlanRepository workPlanRepository) {
        this.workPlanRepository = workPlanRepository;
    }

    @Override
    public WorkPlan save(WorkPlan workPlan) {
        log.debug("Request to save WorkPlan : {}", workPlan);
        return workPlanRepository.save(workPlan);
    }

    @Override
    public WorkPlan update(WorkPlan workPlan) {
        log.debug("Request to update WorkPlan : {}", workPlan);
        return workPlanRepository.save(workPlan);
    }

    @Override
    public Optional<WorkPlan> partialUpdate(WorkPlan workPlan) {
        log.debug("Request to partially update WorkPlan : {}", workPlan);

        return workPlanRepository
            .findById(workPlan.getId())
            .map(existingWorkPlan -> {
                if (workPlan.getTitleUz() != null) {
                    existingWorkPlan.setTitleUz(workPlan.getTitleUz());
                }
                if (workPlan.getTitleRu() != null) {
                    existingWorkPlan.setTitleRu(workPlan.getTitleRu());
                }
                if (workPlan.getTitleKr() != null) {
                    existingWorkPlan.setTitleKr(workPlan.getTitleKr());
                }
                if (workPlan.getContentUz() != null) {
                    existingWorkPlan.setContentUz(workPlan.getContentUz());
                }
                if (workPlan.getContentRu() != null) {
                    existingWorkPlan.setContentRu(workPlan.getContentRu());
                }
                if (workPlan.getContentKr() != null) {
                    existingWorkPlan.setContentKr(workPlan.getContentKr());
                }
                if (workPlan.getPlanType() != null) {
                    existingWorkPlan.setPlanType(workPlan.getPlanType());
                }

                return existingWorkPlan;
            })
            .map(workPlanRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkPlan> findAll() {
        log.debug("Request to get all WorkPlans");
        return workPlanRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkPlan> findOne(Long id) {
        log.debug("Request to get WorkPlan : {}", id);
        return workPlanRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkPlan : {}", id);
        workPlanRepository.deleteById(id);
    }
}
