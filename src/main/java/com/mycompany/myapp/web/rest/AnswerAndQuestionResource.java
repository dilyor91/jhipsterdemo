package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AnswerAndQuestion;
import com.mycompany.myapp.repository.AnswerAndQuestionRepository;
import com.mycompany.myapp.service.AnswerAndQuestionService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AnswerAndQuestion}.
 */
@RestController
@RequestMapping("/api")
public class AnswerAndQuestionResource {

    private final Logger log = LoggerFactory.getLogger(AnswerAndQuestionResource.class);

    private static final String ENTITY_NAME = "answerAndQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerAndQuestionService answerAndQuestionService;

    private final AnswerAndQuestionRepository answerAndQuestionRepository;

    public AnswerAndQuestionResource(
        AnswerAndQuestionService answerAndQuestionService,
        AnswerAndQuestionRepository answerAndQuestionRepository
    ) {
        this.answerAndQuestionService = answerAndQuestionService;
        this.answerAndQuestionRepository = answerAndQuestionRepository;
    }

    /**
     * {@code POST  /answer-and-questions} : Create a new answerAndQuestion.
     *
     * @param answerAndQuestion the answerAndQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerAndQuestion, or with status {@code 400 (Bad Request)} if the answerAndQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-and-questions")
    public ResponseEntity<AnswerAndQuestion> createAnswerAndQuestion(@RequestBody AnswerAndQuestion answerAndQuestion)
        throws URISyntaxException {
        log.debug("REST request to save AnswerAndQuestion : {}", answerAndQuestion);
        if (answerAndQuestion.getId() != null) {
            throw new BadRequestAlertException("A new answerAndQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerAndQuestion result = answerAndQuestionService.save(answerAndQuestion);
        return ResponseEntity
            .created(new URI("/api/answer-and-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-and-questions/:id} : Updates an existing answerAndQuestion.
     *
     * @param id the id of the answerAndQuestion to save.
     * @param answerAndQuestion the answerAndQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerAndQuestion,
     * or with status {@code 400 (Bad Request)} if the answerAndQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerAndQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-and-questions/{id}")
    public ResponseEntity<AnswerAndQuestion> updateAnswerAndQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnswerAndQuestion answerAndQuestion
    ) throws URISyntaxException {
        log.debug("REST request to update AnswerAndQuestion : {}, {}", id, answerAndQuestion);
        if (answerAndQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, answerAndQuestion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!answerAndQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnswerAndQuestion result = answerAndQuestionService.update(answerAndQuestion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerAndQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /answer-and-questions/:id} : Partial updates given fields of an existing answerAndQuestion, field will ignore if it is null
     *
     * @param id the id of the answerAndQuestion to save.
     * @param answerAndQuestion the answerAndQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerAndQuestion,
     * or with status {@code 400 (Bad Request)} if the answerAndQuestion is not valid,
     * or with status {@code 404 (Not Found)} if the answerAndQuestion is not found,
     * or with status {@code 500 (Internal Server Error)} if the answerAndQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/answer-and-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnswerAndQuestion> partialUpdateAnswerAndQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnswerAndQuestion answerAndQuestion
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnswerAndQuestion partially : {}, {}", id, answerAndQuestion);
        if (answerAndQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, answerAndQuestion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!answerAndQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnswerAndQuestion> result = answerAndQuestionService.partialUpdate(answerAndQuestion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerAndQuestion.getId().toString())
        );
    }

    /**
     * {@code GET  /answer-and-questions} : get all the answerAndQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerAndQuestions in body.
     */
    @GetMapping("/answer-and-questions")
    public List<AnswerAndQuestion> getAllAnswerAndQuestions() {
        log.debug("REST request to get all AnswerAndQuestions");
        return answerAndQuestionService.findAll();
    }

    /**
     * {@code GET  /answer-and-questions/:id} : get the "id" answerAndQuestion.
     *
     * @param id the id of the answerAndQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerAndQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-and-questions/{id}")
    public ResponseEntity<AnswerAndQuestion> getAnswerAndQuestion(@PathVariable Long id) {
        log.debug("REST request to get AnswerAndQuestion : {}", id);
        Optional<AnswerAndQuestion> answerAndQuestion = answerAndQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerAndQuestion);
    }

    /**
     * {@code DELETE  /answer-and-questions/:id} : delete the "id" answerAndQuestion.
     *
     * @param id the id of the answerAndQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-and-questions/{id}")
    public ResponseEntity<Void> deleteAnswerAndQuestion(@PathVariable Long id) {
        log.debug("REST request to delete AnswerAndQuestion : {}", id);
        answerAndQuestionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
