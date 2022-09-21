package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.OurHistory;
import com.mycompany.myapp.repository.OurHistoryRepository;
import com.mycompany.myapp.service.OurHistoryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OurHistory}.
 */
@Service
@Transactional
public class OurHistoryServiceImpl implements OurHistoryService {

    private final Logger log = LoggerFactory.getLogger(OurHistoryServiceImpl.class);

    private final OurHistoryRepository ourHistoryRepository;

    public OurHistoryServiceImpl(OurHistoryRepository ourHistoryRepository) {
        this.ourHistoryRepository = ourHistoryRepository;
    }

    @Override
    public OurHistory save(OurHistory ourHistory) {
        log.debug("Request to save OurHistory : {}", ourHistory);
        return ourHistoryRepository.save(ourHistory);
    }

    @Override
    public OurHistory update(OurHistory ourHistory) {
        log.debug("Request to update OurHistory : {}", ourHistory);
        return ourHistoryRepository.save(ourHistory);
    }

    @Override
    public Optional<OurHistory> partialUpdate(OurHistory ourHistory) {
        log.debug("Request to partially update OurHistory : {}", ourHistory);

        return ourHistoryRepository
            .findById(ourHistory.getId())
            .map(existingOurHistory -> {
                if (ourHistory.getContentUz() != null) {
                    existingOurHistory.setContentUz(ourHistory.getContentUz());
                }
                if (ourHistory.getContentRu() != null) {
                    existingOurHistory.setContentRu(ourHistory.getContentRu());
                }
                if (ourHistory.getContentKr() != null) {
                    existingOurHistory.setContentKr(ourHistory.getContentKr());
                }
                if (ourHistory.getPostedDate() != null) {
                    existingOurHistory.setPostedDate(ourHistory.getPostedDate());
                }

                return existingOurHistory;
            })
            .map(ourHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OurHistory> findAll() {
        log.debug("Request to get all OurHistories");
        return ourHistoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OurHistory> findOne(Long id) {
        log.debug("Request to get OurHistory : {}", id);
        return ourHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OurHistory : {}", id);
        ourHistoryRepository.deleteById(id);
    }
}
