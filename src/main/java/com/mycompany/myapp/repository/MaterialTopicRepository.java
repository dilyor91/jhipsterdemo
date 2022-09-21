package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterialTopic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaterialTopic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialTopicRepository extends JpaRepository<MaterialTopic, Long> {}
