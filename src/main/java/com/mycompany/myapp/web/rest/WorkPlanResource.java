package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.WorkPlan;
import com.mycompany.myapp.repository.WorkPlanRepository;
import com.mycompany.myapp.service.WorkPlanService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.WorkPlan}.
 */
@RestController
@RequestMapping("/api")
public class WorkPlanResource {

    private final Logger log = LoggerFactory.getLogger(WorkPlanResource.class);

    private static final String ENTITY_NAME = "workPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkPlanService workPlanService;

    private final WorkPlanRepository workPlanRepository;

    public WorkPlanResource(WorkPlanService workPlanService, WorkPlanRepository workPlanRepository) {
        this.workPlanService = workPlanService;
        this.workPlanRepository = workPlanRepository;
    }

    /**
     * {@code POST  /work-plans} : Create a new workPlan.
     *
     * @param workPlan the workPlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workPlan, or with status {@code 400 (Bad Request)} if the workPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-plans")
    public ResponseEntity<WorkPlan> createWorkPlan(@RequestBody WorkPlan workPlan) throws URISyntaxException {
        log.debug("REST request to save WorkPlan : {}", workPlan);
        if (workPlan.getId() != null) {
            throw new BadRequestAlertException("A new workPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkPlan result = workPlanService.save(workPlan);
        return ResponseEntity
            .created(new URI("/api/work-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-plans/:id} : Updates an existing workPlan.
     *
     * @param id the id of the workPlan to save.
     * @param workPlan the workPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workPlan,
     * or with status {@code 400 (Bad Request)} if the workPlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-plans/{id}")
    public ResponseEntity<WorkPlan> updateWorkPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkPlan workPlan
    ) throws URISyntaxException {
        log.debug("REST request to update WorkPlan : {}, {}", id, workPlan);
        if (workPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workPlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkPlan result = workPlanService.update(workPlan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workPlan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-plans/:id} : Partial updates given fields of an existing workPlan, field will ignore if it is null
     *
     * @param id the id of the workPlan to save.
     * @param workPlan the workPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workPlan,
     * or with status {@code 400 (Bad Request)} if the workPlan is not valid,
     * or with status {@code 404 (Not Found)} if the workPlan is not found,
     * or with status {@code 500 (Internal Server Error)} if the workPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-plans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkPlan> partialUpdateWorkPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkPlan workPlan
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkPlan partially : {}, {}", id, workPlan);
        if (workPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workPlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkPlan> result = workPlanService.partialUpdate(workPlan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workPlan.getId().toString())
        );
    }

    /**
     * {@code GET  /work-plans} : get all the workPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workPlans in body.
     */
    @GetMapping("/work-plans")
    public List<WorkPlan> getAllWorkPlans() {
        log.debug("REST request to get all WorkPlans");
        return workPlanService.findAll();
    }

    /**
     * {@code GET  /work-plans/:id} : get the "id" workPlan.
     *
     * @param id the id of the workPlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workPlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-plans/{id}")
    public ResponseEntity<WorkPlan> getWorkPlan(@PathVariable Long id) {
        log.debug("REST request to get WorkPlan : {}", id);
        Optional<WorkPlan> workPlan = workPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workPlan);
    }

    /**
     * {@code DELETE  /work-plans/:id} : delete the "id" workPlan.
     *
     * @param id the id of the workPlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-plans/{id}")
    public ResponseEntity<Void> deleteWorkPlan(@PathVariable Long id) {
        log.debug("REST request to delete WorkPlan : {}", id);
        workPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
