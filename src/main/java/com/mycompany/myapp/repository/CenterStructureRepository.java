package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CenterStructure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CenterStructure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterStructureRepository extends JpaRepository<CenterStructure, Long> {}
