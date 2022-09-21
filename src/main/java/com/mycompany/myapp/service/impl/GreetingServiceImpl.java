package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Greeting;
import com.mycompany.myapp.repository.GreetingRepository;
import com.mycompany.myapp.service.GreetingService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Greeting}.
 */
@Service
@Transactional
public class GreetingServiceImpl implements GreetingService {

    private final Logger log = LoggerFactory.getLogger(GreetingServiceImpl.class);

    private final GreetingRepository greetingRepository;

    public GreetingServiceImpl(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public Greeting save(Greeting greeting) {
        log.debug("Request to save Greeting : {}", greeting);
        return greetingRepository.save(greeting);
    }

    @Override
    public Greeting update(Greeting greeting) {
        log.debug("Request to update Greeting : {}", greeting);
        return greetingRepository.save(greeting);
    }

    @Override
    public Optional<Greeting> partialUpdate(Greeting greeting) {
        log.debug("Request to partially update Greeting : {}", greeting);

        return greetingRepository
            .findById(greeting.getId())
            .map(existingGreeting -> {
                if (greeting.getContentUz() != null) {
                    existingGreeting.setContentUz(greeting.getContentUz());
                }
                if (greeting.getContentRu() != null) {
                    existingGreeting.setContentRu(greeting.getContentRu());
                }
                if (greeting.getContentKr() != null) {
                    existingGreeting.setContentKr(greeting.getContentKr());
                }

                return existingGreeting;
            })
            .map(greetingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Greeting> findAll() {
        log.debug("Request to get all Greetings");
        return greetingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Greeting> findOne(Long id) {
        log.debug("Request to get Greeting : {}", id);
        return greetingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Greeting : {}", id);
        greetingRepository.deleteById(id);
    }
}
