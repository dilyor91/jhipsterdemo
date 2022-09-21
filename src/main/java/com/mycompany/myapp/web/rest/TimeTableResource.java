package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TimeTable;
import com.mycompany.myapp.repository.TimeTableRepository;
import com.mycompany.myapp.service.TimeTableService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TimeTable}.
 */
@RestController
@RequestMapping("/api")
public class TimeTableResource {

    private final Logger log = LoggerFactory.getLogger(TimeTableResource.class);

    private static final String ENTITY_NAME = "timeTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeTableService timeTableService;

    private final TimeTableRepository timeTableRepository;

    public TimeTableResource(TimeTableService timeTableService, TimeTableRepository timeTableRepository) {
        this.timeTableService = timeTableService;
        this.timeTableRepository = timeTableRepository;
    }

    /**
     * {@code POST  /time-tables} : Create a new timeTable.
     *
     * @param timeTable the timeTable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeTable, or with status {@code 400 (Bad Request)} if the timeTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-tables")
    public ResponseEntity<TimeTable> createTimeTable(@RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to save TimeTable : {}", timeTable);
        if (timeTable.getId() != null) {
            throw new BadRequestAlertException("A new timeTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeTable result = timeTableService.save(timeTable);
        return ResponseEntity
            .created(new URI("/api/time-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-tables/:id} : Updates an existing timeTable.
     *
     * @param id the id of the timeTable to save.
     * @param timeTable the timeTable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeTable,
     * or with status {@code 400 (Bad Request)} if the timeTable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeTable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-tables/{id}")
    public ResponseEntity<TimeTable> updateTimeTable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeTable timeTable
    ) throws URISyntaxException {
        log.debug("REST request to update TimeTable : {}, {}", id, timeTable);
        if (timeTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeTable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeTableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TimeTable result = timeTableService.update(timeTable);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeTable.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /time-tables/:id} : Partial updates given fields of an existing timeTable, field will ignore if it is null
     *
     * @param id the id of the timeTable to save.
     * @param timeTable the timeTable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeTable,
     * or with status {@code 400 (Bad Request)} if the timeTable is not valid,
     * or with status {@code 404 (Not Found)} if the timeTable is not found,
     * or with status {@code 500 (Internal Server Error)} if the timeTable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/time-tables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TimeTable> partialUpdateTimeTable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeTable timeTable
    ) throws URISyntaxException {
        log.debug("REST request to partial update TimeTable partially : {}, {}", id, timeTable);
        if (timeTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeTable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeTableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TimeTable> result = timeTableService.partialUpdate(timeTable);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeTable.getId().toString())
        );
    }

    /**
     * {@code GET  /time-tables} : get all the timeTables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeTables in body.
     */
    @GetMapping("/time-tables")
    public List<TimeTable> getAllTimeTables() {
        log.debug("REST request to get all TimeTables");
        return timeTableService.findAll();
    }

    /**
     * {@code GET  /time-tables/:id} : get the "id" timeTable.
     *
     * @param id the id of the timeTable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeTable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-tables/{id}")
    public ResponseEntity<TimeTable> getTimeTable(@PathVariable Long id) {
        log.debug("REST request to get TimeTable : {}", id);
        Optional<TimeTable> timeTable = timeTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeTable);
    }

    /**
     * {@code DELETE  /time-tables/:id} : delete the "id" timeTable.
     *
     * @param id the id of the timeTable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-tables/{id}")
    public ResponseEntity<Void> deleteTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete TimeTable : {}", id);
        timeTableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
