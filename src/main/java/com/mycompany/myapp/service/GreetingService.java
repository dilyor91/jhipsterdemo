package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Greeting;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Greeting}.
 */
public interface GreetingService {
    /**
     * Save a greeting.
     *
     * @param greeting the entity to save.
     * @return the persisted entity.
     */
    Greeting save(Greeting greeting);

    /**
     * Updates a greeting.
     *
     * @param greeting the entity to update.
     * @return the persisted entity.
     */
    Greeting update(Greeting greeting);

    /**
     * Partially updates a greeting.
     *
     * @param greeting the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Greeting> partialUpdate(Greeting greeting);

    /**
     * Get all the greetings.
     *
     * @return the list of entities.
     */
    List<Greeting> findAll();

    /**
     * Get the "id" greeting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Greeting> findOne(Long id);

    /**
     * Delete the "id" greeting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
