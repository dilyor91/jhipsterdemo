package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Greeting;
import com.mycompany.myapp.repository.GreetingRepository;
import com.mycompany.myapp.service.GreetingService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Greeting}.
 */
@RestController
@RequestMapping("/api")
public class GreetingResource {

    private final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    private static final String ENTITY_NAME = "greeting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GreetingService greetingService;

    private final GreetingRepository greetingRepository;

    public GreetingResource(GreetingService greetingService, GreetingRepository greetingRepository) {
        this.greetingService = greetingService;
        this.greetingRepository = greetingRepository;
    }

    /**
     * {@code POST  /greetings} : Create a new greeting.
     *
     * @param greeting the greeting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new greeting, or with status {@code 400 (Bad Request)} if the greeting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/greetings")
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) throws URISyntaxException {
        log.debug("REST request to save Greeting : {}", greeting);
        if (greeting.getId() != null) {
            throw new BadRequestAlertException("A new greeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Greeting result = greetingService.save(greeting);
        return ResponseEntity
            .created(new URI("/api/greetings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /greetings/:id} : Updates an existing greeting.
     *
     * @param id the id of the greeting to save.
     * @param greeting the greeting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated greeting,
     * or with status {@code 400 (Bad Request)} if the greeting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the greeting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/greetings/{id}")
    public ResponseEntity<Greeting> updateGreeting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Greeting greeting
    ) throws URISyntaxException {
        log.debug("REST request to update Greeting : {}, {}", id, greeting);
        if (greeting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, greeting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!greetingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Greeting result = greetingService.update(greeting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, greeting.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /greetings/:id} : Partial updates given fields of an existing greeting, field will ignore if it is null
     *
     * @param id the id of the greeting to save.
     * @param greeting the greeting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated greeting,
     * or with status {@code 400 (Bad Request)} if the greeting is not valid,
     * or with status {@code 404 (Not Found)} if the greeting is not found,
     * or with status {@code 500 (Internal Server Error)} if the greeting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/greetings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Greeting> partialUpdateGreeting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Greeting greeting
    ) throws URISyntaxException {
        log.debug("REST request to partial update Greeting partially : {}, {}", id, greeting);
        if (greeting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, greeting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!greetingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Greeting> result = greetingService.partialUpdate(greeting);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, greeting.getId().toString())
        );
    }

    /**
     * {@code GET  /greetings} : get all the greetings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of greetings in body.
     */
    @GetMapping("/greetings")
    public List<Greeting> getAllGreetings() {
        log.debug("REST request to get all Greetings");
        return greetingService.findAll();
    }

    /**
     * {@code GET  /greetings/:id} : get the "id" greeting.
     *
     * @param id the id of the greeting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the greeting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/greetings/{id}")
    public ResponseEntity<Greeting> getGreeting(@PathVariable Long id) {
        log.debug("REST request to get Greeting : {}", id);
        Optional<Greeting> greeting = greetingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(greeting);
    }

    /**
     * {@code DELETE  /greetings/:id} : delete the "id" greeting.
     *
     * @param id the id of the greeting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/greetings/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        log.debug("REST request to delete Greeting : {}", id);
        greetingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
