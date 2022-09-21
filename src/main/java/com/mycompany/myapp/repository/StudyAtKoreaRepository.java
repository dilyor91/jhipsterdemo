package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StudyAtKorea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StudyAtKorea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudyAtKoreaRepository extends JpaRepository<StudyAtKorea, Long> {}
