package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Institution;
import com.mycompany.myapp.repository.InstitutionRepository;
import com.mycompany.myapp.service.InstitutionService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Institution}.
 */
@RestController
@RequestMapping("/api")
public class InstitutionResource {

    private final Logger log = LoggerFactory.getLogger(InstitutionResource.class);

    private static final String ENTITY_NAME = "institution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstitutionService institutionService;

    private final InstitutionRepository institutionRepository;

    public InstitutionResource(InstitutionService institutionService, InstitutionRepository institutionRepository) {
        this.institutionService = institutionService;
        this.institutionRepository = institutionRepository;
    }

    /**
     * {@code POST  /institutions} : Create a new institution.
     *
     * @param institution the institution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institution, or with status {@code 400 (Bad Request)} if the institution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institutions")
    public ResponseEntity<Institution> createInstitution(@RequestBody Institution institution) throws URISyntaxException {
        log.debug("REST request to save Institution : {}", institution);
        if (institution.getId() != null) {
            throw new BadRequestAlertException("A new institution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Institution result = institutionService.save(institution);
        return ResponseEntity
            .created(new URI("/api/institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institutions/:id} : Updates an existing institution.
     *
     * @param id the id of the institution to save.
     * @param institution the institution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institution,
     * or with status {@code 400 (Bad Request)} if the institution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the institution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institutions/{id}")
    public ResponseEntity<Institution> updateInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Institution institution
    ) throws URISyntaxException {
        log.debug("REST request to update Institution : {}, {}", id, institution);
        if (institution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, institution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!institutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Institution result = institutionService.update(institution);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institution.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /institutions/:id} : Partial updates given fields of an existing institution, field will ignore if it is null
     *
     * @param id the id of the institution to save.
     * @param institution the institution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institution,
     * or with status {@code 400 (Bad Request)} if the institution is not valid,
     * or with status {@code 404 (Not Found)} if the institution is not found,
     * or with status {@code 500 (Internal Server Error)} if the institution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/institutions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Institution> partialUpdateInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Institution institution
    ) throws URISyntaxException {
        log.debug("REST request to partial update Institution partially : {}, {}", id, institution);
        if (institution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, institution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!institutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Institution> result = institutionService.partialUpdate(institution);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institution.getId().toString())
        );
    }

    /**
     * {@code GET  /institutions} : get all the institutions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institutions in body.
     */
    @GetMapping("/institutions")
    public List<Institution> getAllInstitutions() {
        log.debug("REST request to get all Institutions");
        return institutionService.findAll();
    }

    /**
     * {@code GET  /institutions/:id} : get the "id" institution.
     *
     * @param id the id of the institution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institutions/{id}")
    public ResponseEntity<Institution> getInstitution(@PathVariable Long id) {
        log.debug("REST request to get Institution : {}", id);
        Optional<Institution> institution = institutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(institution);
    }

    /**
     * {@code DELETE  /institutions/:id} : delete the "id" institution.
     *
     * @param id the id of the institution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        log.debug("REST request to delete Institution : {}", id);
        institutionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
