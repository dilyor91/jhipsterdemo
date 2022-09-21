package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OurHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OurHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OurHistoryRepository extends JpaRepository<OurHistory, Long> {}
