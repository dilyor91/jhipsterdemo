package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FileTopic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FileTopic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileTopicRepository extends JpaRepository<FileTopic, Long> {}
