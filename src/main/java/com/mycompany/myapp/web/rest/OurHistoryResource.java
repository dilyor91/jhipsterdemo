package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.OurHistory;
import com.mycompany.myapp.repository.OurHistoryRepository;
import com.mycompany.myapp.service.OurHistoryService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.OurHistory}.
 */
@RestController
@RequestMapping("/api")
public class OurHistoryResource {

    private final Logger log = LoggerFactory.getLogger(OurHistoryResource.class);

    private static final String ENTITY_NAME = "ourHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OurHistoryService ourHistoryService;

    private final OurHistoryRepository ourHistoryRepository;

    public OurHistoryResource(OurHistoryService ourHistoryService, OurHistoryRepository ourHistoryRepository) {
        this.ourHistoryService = ourHistoryService;
        this.ourHistoryRepository = ourHistoryRepository;
    }

    /**
     * {@code POST  /our-histories} : Create a new ourHistory.
     *
     * @param ourHistory the ourHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ourHistory, or with status {@code 400 (Bad Request)} if the ourHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/our-histories")
    public ResponseEntity<OurHistory> createOurHistory(@RequestBody OurHistory ourHistory) throws URISyntaxException {
        log.debug("REST request to save OurHistory : {}", ourHistory);
        if (ourHistory.getId() != null) {
            throw new BadRequestAlertException("A new ourHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OurHistory result = ourHistoryService.save(ourHistory);
        return ResponseEntity
            .created(new URI("/api/our-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /our-histories/:id} : Updates an existing ourHistory.
     *
     * @param id the id of the ourHistory to save.
     * @param ourHistory the ourHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ourHistory,
     * or with status {@code 400 (Bad Request)} if the ourHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ourHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/our-histories/{id}")
    public ResponseEntity<OurHistory> updateOurHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OurHistory ourHistory
    ) throws URISyntaxException {
        log.debug("REST request to update OurHistory : {}, {}", id, ourHistory);
        if (ourHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ourHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ourHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OurHistory result = ourHistoryService.update(ourHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ourHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /our-histories/:id} : Partial updates given fields of an existing ourHistory, field will ignore if it is null
     *
     * @param id the id of the ourHistory to save.
     * @param ourHistory the ourHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ourHistory,
     * or with status {@code 400 (Bad Request)} if the ourHistory is not valid,
     * or with status {@code 404 (Not Found)} if the ourHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the ourHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/our-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OurHistory> partialUpdateOurHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OurHistory ourHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update OurHistory partially : {}, {}", id, ourHistory);
        if (ourHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ourHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ourHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OurHistory> result = ourHistoryService.partialUpdate(ourHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ourHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /our-histories} : get all the ourHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ourHistories in body.
     */
    @GetMapping("/our-histories")
    public List<OurHistory> getAllOurHistories() {
        log.debug("REST request to get all OurHistories");
        return ourHistoryService.findAll();
    }

    /**
     * {@code GET  /our-histories/:id} : get the "id" ourHistory.
     *
     * @param id the id of the ourHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ourHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/our-histories/{id}")
    public ResponseEntity<OurHistory> getOurHistory(@PathVariable Long id) {
        log.debug("REST request to get OurHistory : {}", id);
        Optional<OurHistory> ourHistory = ourHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ourHistory);
    }

    /**
     * {@code DELETE  /our-histories/:id} : delete the "id" ourHistory.
     *
     * @param id the id of the ourHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/our-histories/{id}")
    public ResponseEntity<Void> deleteOurHistory(@PathVariable Long id) {
        log.debug("REST request to delete OurHistory : {}", id);
        ourHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
