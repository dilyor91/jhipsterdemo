package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Events;
import com.mycompany.myapp.repository.EventsRepository;
import com.mycompany.myapp.service.EventsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Events}.
 */
@Service
@Transactional
public class EventsServiceImpl implements EventsService {

    private final Logger log = LoggerFactory.getLogger(EventsServiceImpl.class);

    private final EventsRepository eventsRepository;

    public EventsServiceImpl(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Events save(Events events) {
        log.debug("Request to save Events : {}", events);
        return eventsRepository.save(events);
    }

    @Override
    public Events update(Events events) {
        log.debug("Request to update Events : {}", events);
        return eventsRepository.save(events);
    }

    @Override
    public Optional<Events> partialUpdate(Events events) {
        log.debug("Request to partially update Events : {}", events);

        return eventsRepository
            .findById(events.getId())
            .map(existingEvents -> {
                if (events.getTitleUz() != null) {
                    existingEvents.setTitleUz(events.getTitleUz());
                }
                if (events.getTitleRu() != null) {
                    existingEvents.setTitleRu(events.getTitleRu());
                }
                if (events.getTitleKr() != null) {
                    existingEvents.setTitleKr(events.getTitleKr());
                }
                if (events.getContentUz() != null) {
                    existingEvents.setContentUz(events.getContentUz());
                }
                if (events.getContentRu() != null) {
                    existingEvents.setContentRu(events.getContentRu());
                }
                if (events.getContentKr() != null) {
                    existingEvents.setContentKr(events.getContentKr());
                }
                if (events.getPostedDate() != null) {
                    existingEvents.setPostedDate(events.getPostedDate());
                }
                if (events.getStatus() != null) {
                    existingEvents.setStatus(events.getStatus());
                }

                return existingEvents;
            })
            .map(eventsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Events> findAll() {
        log.debug("Request to get all Events");
        return eventsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Events> findOne(Long id) {
        log.debug("Request to get Events : {}", id);
        return eventsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Events : {}", id);
        eventsRepository.deleteById(id);
    }
}
