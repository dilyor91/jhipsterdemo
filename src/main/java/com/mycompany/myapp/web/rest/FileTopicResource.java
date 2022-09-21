package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FileTopic;
import com.mycompany.myapp.repository.FileTopicRepository;
import com.mycompany.myapp.service.FileTopicService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FileTopic}.
 */
@RestController
@RequestMapping("/api")
public class FileTopicResource {

    private final Logger log = LoggerFactory.getLogger(FileTopicResource.class);

    private static final String ENTITY_NAME = "fileTopic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileTopicService fileTopicService;

    private final FileTopicRepository fileTopicRepository;

    public FileTopicResource(FileTopicService fileTopicService, FileTopicRepository fileTopicRepository) {
        this.fileTopicService = fileTopicService;
        this.fileTopicRepository = fileTopicRepository;
    }

    /**
     * {@code POST  /file-topics} : Create a new fileTopic.
     *
     * @param fileTopic the fileTopic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileTopic, or with status {@code 400 (Bad Request)} if the fileTopic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-topics")
    public ResponseEntity<FileTopic> createFileTopic(@RequestBody FileTopic fileTopic) throws URISyntaxException {
        log.debug("REST request to save FileTopic : {}", fileTopic);
        if (fileTopic.getId() != null) {
            throw new BadRequestAlertException("A new fileTopic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileTopic result = fileTopicService.save(fileTopic);
        return ResponseEntity
            .created(new URI("/api/file-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-topics/:id} : Updates an existing fileTopic.
     *
     * @param id the id of the fileTopic to save.
     * @param fileTopic the fileTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileTopic,
     * or with status {@code 400 (Bad Request)} if the fileTopic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-topics/{id}")
    public ResponseEntity<FileTopic> updateFileTopic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileTopic fileTopic
    ) throws URISyntaxException {
        log.debug("REST request to update FileTopic : {}, {}", id, fileTopic);
        if (fileTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileTopic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileTopicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileTopic result = fileTopicService.update(fileTopic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileTopic.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-topics/:id} : Partial updates given fields of an existing fileTopic, field will ignore if it is null
     *
     * @param id the id of the fileTopic to save.
     * @param fileTopic the fileTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileTopic,
     * or with status {@code 400 (Bad Request)} if the fileTopic is not valid,
     * or with status {@code 404 (Not Found)} if the fileTopic is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-topics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileTopic> partialUpdateFileTopic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileTopic fileTopic
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileTopic partially : {}, {}", id, fileTopic);
        if (fileTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileTopic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileTopicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileTopic> result = fileTopicService.partialUpdate(fileTopic);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileTopic.getId().toString())
        );
    }

    /**
     * {@code GET  /file-topics} : get all the fileTopics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileTopics in body.
     */
    @GetMapping("/file-topics")
    public List<FileTopic> getAllFileTopics() {
        log.debug("REST request to get all FileTopics");
        return fileTopicService.findAll();
    }

    /**
     * {@code GET  /file-topics/:id} : get the "id" fileTopic.
     *
     * @param id the id of the fileTopic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileTopic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-topics/{id}")
    public ResponseEntity<FileTopic> getFileTopic(@PathVariable Long id) {
        log.debug("REST request to get FileTopic : {}", id);
        Optional<FileTopic> fileTopic = fileTopicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileTopic);
    }

    /**
     * {@code DELETE  /file-topics/:id} : delete the "id" fileTopic.
     *
     * @param id the id of the fileTopic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-topics/{id}")
    public ResponseEntity<Void> deleteFileTopic(@PathVariable Long id) {
        log.debug("REST request to delete FileTopic : {}", id);
        fileTopicService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
