package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AnswerAndQuestion;
import com.mycompany.myapp.repository.AnswerAndQuestionRepository;
import com.mycompany.myapp.service.AnswerAndQuestionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnswerAndQuestion}.
 */
@Service
@Transactional
public class AnswerAndQuestionServiceImpl implements AnswerAndQuestionService {

    private final Logger log = LoggerFactory.getLogger(AnswerAndQuestionServiceImpl.class);

    private final AnswerAndQuestionRepository answerAndQuestionRepository;

    public AnswerAndQuestionServiceImpl(AnswerAndQuestionRepository answerAndQuestionRepository) {
        this.answerAndQuestionRepository = answerAndQuestionRepository;
    }

    @Override
    public AnswerAndQuestion save(AnswerAndQuestion answerAndQuestion) {
        log.debug("Request to save AnswerAndQuestion : {}", answerAndQuestion);
        return answerAndQuestionRepository.save(answerAndQuestion);
    }

    @Override
    public AnswerAndQuestion update(AnswerAndQuestion answerAndQuestion) {
        log.debug("Request to update AnswerAndQuestion : {}", answerAndQuestion);
        return answerAndQuestionRepository.save(answerAndQuestion);
    }

    @Override
    public Optional<AnswerAndQuestion> partialUpdate(AnswerAndQuestion answerAndQuestion) {
        log.debug("Request to partially update AnswerAndQuestion : {}", answerAndQuestion);

        return answerAndQuestionRepository
            .findById(answerAndQuestion.getId())
            .map(existingAnswerAndQuestion -> {
                if (answerAndQuestion.getQuestionUz() != null) {
                    existingAnswerAndQuestion.setQuestionUz(answerAndQuestion.getQuestionUz());
                }
                if (answerAndQuestion.getQuestionRu() != null) {
                    existingAnswerAndQuestion.setQuestionRu(answerAndQuestion.getQuestionRu());
                }
                if (answerAndQuestion.getQuestionKr() != null) {
                    existingAnswerAndQuestion.setQuestionKr(answerAndQuestion.getQuestionKr());
                }
                if (answerAndQuestion.getAnswerUz() != null) {
                    existingAnswerAndQuestion.setAnswerUz(answerAndQuestion.getAnswerUz());
                }
                if (answerAndQuestion.getAnswerRu() != null) {
                    existingAnswerAndQuestion.setAnswerRu(answerAndQuestion.getAnswerRu());
                }
                if (answerAndQuestion.getAnswerKr() != null) {
                    existingAnswerAndQuestion.setAnswerKr(answerAndQuestion.getAnswerKr());
                }

                return existingAnswerAndQuestion;
            })
            .map(answerAndQuestionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerAndQuestion> findAll() {
        log.debug("Request to get all AnswerAndQuestions");
        return answerAndQuestionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerAndQuestion> findOne(Long id) {
        log.debug("Request to get AnswerAndQuestion : {}", id);
        return answerAndQuestionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerAndQuestion : {}", id);
        answerAndQuestionRepository.deleteById(id);
    }
}
