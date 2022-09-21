package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaterialTopic;
import com.mycompany.myapp.repository.MaterialTopicRepository;
import com.mycompany.myapp.service.MaterialTopicService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterialTopic}.
 */
@RestController
@RequestMapping("/api")
public class MaterialTopicResource {

    private final Logger log = LoggerFactory.getLogger(MaterialTopicResource.class);

    private static final String ENTITY_NAME = "materialTopic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialTopicService materialTopicService;

    private final MaterialTopicRepository materialTopicRepository;

    public MaterialTopicResource(MaterialTopicService materialTopicService, MaterialTopicRepository materialTopicRepository) {
        this.materialTopicService = materialTopicService;
        this.materialTopicRepository = materialTopicRepository;
    }

    /**
     * {@code POST  /material-topics} : Create a new materialTopic.
     *
     * @param materialTopic the materialTopic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialTopic, or with status {@code 400 (Bad Request)} if the materialTopic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-topics")
    public ResponseEntity<MaterialTopic> createMaterialTopic(@RequestBody MaterialTopic materialTopic) throws URISyntaxException {
        log.debug("REST request to save MaterialTopic : {}", materialTopic);
        if (materialTopic.getId() != null) {
            throw new BadRequestAlertException("A new materialTopic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialTopic result = materialTopicService.save(materialTopic);
        return ResponseEntity
            .created(new URI("/api/material-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-topics/:id} : Updates an existing materialTopic.
     *
     * @param id the id of the materialTopic to save.
     * @param materialTopic the materialTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialTopic,
     * or with status {@code 400 (Bad Request)} if the materialTopic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-topics/{id}")
    public ResponseEntity<MaterialTopic> updateMaterialTopic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialTopic materialTopic
    ) throws URISyntaxException {
        log.debug("REST request to update MaterialTopic : {}, {}", id, materialTopic);
        if (materialTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialTopic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialTopicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaterialTopic result = materialTopicService.update(materialTopic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialTopic.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /material-topics/:id} : Partial updates given fields of an existing materialTopic, field will ignore if it is null
     *
     * @param id the id of the materialTopic to save.
     * @param materialTopic the materialTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialTopic,
     * or with status {@code 400 (Bad Request)} if the materialTopic is not valid,
     * or with status {@code 404 (Not Found)} if the materialTopic is not found,
     * or with status {@code 500 (Internal Server Error)} if the materialTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/material-topics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterialTopic> partialUpdateMaterialTopic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialTopic materialTopic
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaterialTopic partially : {}, {}", id, materialTopic);
        if (materialTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialTopic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialTopicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterialTopic> result = materialTopicService.partialUpdate(materialTopic);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialTopic.getId().toString())
        );
    }

    /**
     * {@code GET  /material-topics} : get all the materialTopics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialTopics in body.
     */
    @GetMapping("/material-topics")
    public List<MaterialTopic> getAllMaterialTopics() {
        log.debug("REST request to get all MaterialTopics");
        return materialTopicService.findAll();
    }

    /**
     * {@code GET  /material-topics/:id} : get the "id" materialTopic.
     *
     * @param id the id of the materialTopic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialTopic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-topics/{id}")
    public ResponseEntity<MaterialTopic> getMaterialTopic(@PathVariable Long id) {
        log.debug("REST request to get MaterialTopic : {}", id);
        Optional<MaterialTopic> materialTopic = materialTopicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialTopic);
    }

    /**
     * {@code DELETE  /material-topics/:id} : delete the "id" materialTopic.
     *
     * @param id the id of the materialTopic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-topics/{id}")
    public ResponseEntity<Void> deleteMaterialTopic(@PathVariable Long id) {
        log.debug("REST request to delete MaterialTopic : {}", id);
        materialTopicService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
