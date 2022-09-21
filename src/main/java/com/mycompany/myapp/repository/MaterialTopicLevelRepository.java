package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterialTopicLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaterialTopicLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialTopicLevelRepository extends JpaRepository<MaterialTopicLevel, Long> {}
