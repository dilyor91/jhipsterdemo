package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AnswerAndQuestion;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AnswerAndQuestion}.
 */
public interface AnswerAndQuestionService {
    /**
     * Save a answerAndQuestion.
     *
     * @param answerAndQuestion the entity to save.
     * @return the persisted entity.
     */
    AnswerAndQuestion save(AnswerAndQuestion answerAndQuestion);

    /**
     * Updates a answerAndQuestion.
     *
     * @param answerAndQuestion the entity to update.
     * @return the persisted entity.
     */
    AnswerAndQuestion update(AnswerAndQuestion answerAndQuestion);

    /**
     * Partially updates a answerAndQuestion.
     *
     * @param answerAndQuestion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnswerAndQuestion> partialUpdate(AnswerAndQuestion answerAndQuestion);

    /**
     * Get all the answerAndQuestions.
     *
     * @return the list of entities.
     */
    List<AnswerAndQuestion> findAll();

    /**
     * Get the "id" answerAndQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerAndQuestion> findOne(Long id);

    /**
     * Delete the "id" answerAndQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
