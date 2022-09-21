package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Logo;
import com.mycompany.myapp.repository.LogoRepository;
import com.mycompany.myapp.service.LogoService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Logo}.
 */
@RestController
@RequestMapping("/api")
public class LogoResource {

    private final Logger log = LoggerFactory.getLogger(LogoResource.class);

    private static final String ENTITY_NAME = "logo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogoService logoService;

    private final LogoRepository logoRepository;

    public LogoResource(LogoService logoService, LogoRepository logoRepository) {
        this.logoService = logoService;
        this.logoRepository = logoRepository;
    }

    /**
     * {@code POST  /logos} : Create a new logo.
     *
     * @param logo the logo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logo, or with status {@code 400 (Bad Request)} if the logo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logos")
    public ResponseEntity<Logo> createLogo(@RequestBody Logo logo) throws URISyntaxException {
        log.debug("REST request to save Logo : {}", logo);
        if (logo.getId() != null) {
            throw new BadRequestAlertException("A new logo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Logo result = logoService.save(logo);
        return ResponseEntity
            .created(new URI("/api/logos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /logos/:id} : Updates an existing logo.
     *
     * @param id the id of the logo to save.
     * @param logo the logo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logo,
     * or with status {@code 400 (Bad Request)} if the logo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logos/{id}")
    public ResponseEntity<Logo> updateLogo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Logo logo)
        throws URISyntaxException {
        log.debug("REST request to update Logo : {}, {}", id, logo);
        if (logo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, logo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Logo result = logoService.update(logo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /logos/:id} : Partial updates given fields of an existing logo, field will ignore if it is null
     *
     * @param id the id of the logo to save.
     * @param logo the logo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logo,
     * or with status {@code 400 (Bad Request)} if the logo is not valid,
     * or with status {@code 404 (Not Found)} if the logo is not found,
     * or with status {@code 500 (Internal Server Error)} if the logo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/logos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Logo> partialUpdateLogo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Logo logo)
        throws URISyntaxException {
        log.debug("REST request to partial update Logo partially : {}, {}", id, logo);
        if (logo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, logo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Logo> result = logoService.partialUpdate(logo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logo.getId().toString())
        );
    }

    /**
     * {@code GET  /logos} : get all the logos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logos in body.
     */
    @GetMapping("/logos")
    public List<Logo> getAllLogos() {
        log.debug("REST request to get all Logos");
        return logoService.findAll();
    }

    /**
     * {@code GET  /logos/:id} : get the "id" logo.
     *
     * @param id the id of the logo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logos/{id}")
    public ResponseEntity<Logo> getLogo(@PathVariable Long id) {
        log.debug("REST request to get Logo : {}", id);
        Optional<Logo> logo = logoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logo);
    }

    /**
     * {@code DELETE  /logos/:id} : delete the "id" logo.
     *
     * @param id the id of the logo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logos/{id}")
    public ResponseEntity<Void> deleteLogo(@PathVariable Long id) {
        log.debug("REST request to delete Logo : {}", id);
        logoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
