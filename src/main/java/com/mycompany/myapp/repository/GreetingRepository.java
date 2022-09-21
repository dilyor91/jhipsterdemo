package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Greeting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Greeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {}
