package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaterialTopicLevel;
import com.mycompany.myapp.repository.MaterialTopicLevelRepository;
import com.mycompany.myapp.service.MaterialTopicLevelService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterialTopicLevel}.
 */
@RestController
@RequestMapping("/api")
public class MaterialTopicLevelResource {

    private final Logger log = LoggerFactory.getLogger(MaterialTopicLevelResource.class);

    private static final String ENTITY_NAME = "materialTopicLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialTopicLevelService materialTopicLevelService;

    private final MaterialTopicLevelRepository materialTopicLevelRepository;

    public MaterialTopicLevelResource(
        MaterialTopicLevelService materialTopicLevelService,
        MaterialTopicLevelRepository materialTopicLevelRepository
    ) {
        this.materialTopicLevelService = materialTopicLevelService;
        this.materialTopicLevelRepository = materialTopicLevelRepository;
    }

    /**
     * {@code POST  /material-topic-levels} : Create a new materialTopicLevel.
     *
     * @param materialTopicLevel the materialTopicLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialTopicLevel, or with status {@code 400 (Bad Request)} if the materialTopicLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-topic-levels")
    public ResponseEntity<MaterialTopicLevel> createMaterialTopicLevel(@RequestBody MaterialTopicLevel materialTopicLevel)
        throws URISyntaxException {
        log.debug("REST request to save MaterialTopicLevel : {}", materialTopicLevel);
        if (materialTopicLevel.getId() != null) {
            throw new BadRequestAlertException("A new materialTopicLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialTopicLevel result = materialTopicLevelService.save(materialTopicLevel);
        return ResponseEntity
            .created(new URI("/api/material-topic-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-topic-levels/:id} : Updates an existing materialTopicLevel.
     *
     * @param id the id of the materialTopicLevel to save.
     * @param materialTopicLevel the materialTopicLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialTopicLevel,
     * or with status {@code 400 (Bad Request)} if the materialTopicLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialTopicLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-topic-levels/{id}")
    public ResponseEntity<MaterialTopicLevel> updateMaterialTopicLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialTopicLevel materialTopicLevel
    ) throws URISyntaxException {
        log.debug("REST request to update MaterialTopicLevel : {}, {}", id, materialTopicLevel);
        if (materialTopicLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialTopicLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialTopicLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaterialTopicLevel result = materialTopicLevelService.update(materialTopicLevel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialTopicLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /material-topic-levels/:id} : Partial updates given fields of an existing materialTopicLevel, field will ignore if it is null
     *
     * @param id the id of the materialTopicLevel to save.
     * @param materialTopicLevel the materialTopicLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialTopicLevel,
     * or with status {@code 400 (Bad Request)} if the materialTopicLevel is not valid,
     * or with status {@code 404 (Not Found)} if the materialTopicLevel is not found,
     * or with status {@code 500 (Internal Server Error)} if the materialTopicLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/material-topic-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterialTopicLevel> partialUpdateMaterialTopicLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialTopicLevel materialTopicLevel
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaterialTopicLevel partially : {}, {}", id, materialTopicLevel);
        if (materialTopicLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialTopicLevel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialTopicLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterialTopicLevel> result = materialTopicLevelService.partialUpdate(materialTopicLevel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialTopicLevel.getId().toString())
        );
    }

    /**
     * {@code GET  /material-topic-levels} : get all the materialTopicLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialTopicLevels in body.
     */
    @GetMapping("/material-topic-levels")
    public List<MaterialTopicLevel> getAllMaterialTopicLevels() {
        log.debug("REST request to get all MaterialTopicLevels");
        return materialTopicLevelService.findAll();
    }

    /**
     * {@code GET  /material-topic-levels/:id} : get the "id" materialTopicLevel.
     *
     * @param id the id of the materialTopicLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialTopicLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-topic-levels/{id}")
    public ResponseEntity<MaterialTopicLevel> getMaterialTopicLevel(@PathVariable Long id) {
        log.debug("REST request to get MaterialTopicLevel : {}", id);
        Optional<MaterialTopicLevel> materialTopicLevel = materialTopicLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialTopicLevel);
    }

    /**
     * {@code DELETE  /material-topic-levels/:id} : delete the "id" materialTopicLevel.
     *
     * @param id the id of the materialTopicLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-topic-levels/{id}")
    public ResponseEntity<Void> deleteMaterialTopicLevel(@PathVariable Long id) {
        log.debug("REST request to delete MaterialTopicLevel : {}", id);
        materialTopicLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
