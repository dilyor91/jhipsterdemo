package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnswerAndQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnswerAndQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerAndQuestionRepository extends JpaRepository<AnswerAndQuestion, Long> {}
