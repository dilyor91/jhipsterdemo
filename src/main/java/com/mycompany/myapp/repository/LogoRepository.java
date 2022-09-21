package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Logo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Logo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {}
