package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CenterStructure;
import com.mycompany.myapp.repository.CenterStructureRepository;
import com.mycompany.myapp.service.CenterStructureService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CenterStructure}.
 */
@Service
@Transactional
public class CenterStructureServiceImpl implements CenterStructureService {

    private final Logger log = LoggerFactory.getLogger(CenterStructureServiceImpl.class);

    private final CenterStructureRepository centerStructureRepository;

    public CenterStructureServiceImpl(CenterStructureRepository centerStructureRepository) {
        this.centerStructureRepository = centerStructureRepository;
    }

    @Override
    public CenterStructure save(CenterStructure centerStructure) {
        log.debug("Request to save CenterStructure : {}", centerStructure);
        return centerStructureRepository.save(centerStructure);
    }

    @Override
    public CenterStructure update(CenterStructure centerStructure) {
        log.debug("Request to update CenterStructure : {}", centerStructure);
        return centerStructureRepository.save(centerStructure);
    }

    @Override
    public Optional<CenterStructure> partialUpdate(CenterStructure centerStructure) {
        log.debug("Request to partially update CenterStructure : {}", centerStructure);

        return centerStructureRepository
            .findById(centerStructure.getId())
            .map(existingCenterStructure -> {
                if (centerStructure.getContentUz() != null) {
                    existingCenterStructure.setContentUz(centerStructure.getContentUz());
                }
                if (centerStructure.getContentRu() != null) {
                    existingCenterStructure.setContentRu(centerStructure.getContentRu());
                }
                if (centerStructure.getContentKr() != null) {
                    existingCenterStructure.setContentKr(centerStructure.getContentKr());
                }

                return existingCenterStructure;
            })
            .map(centerStructureRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CenterStructure> findAll() {
        log.debug("Request to get all CenterStructures");
        return centerStructureRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CenterStructure> findOne(Long id) {
        log.debug("Request to get CenterStructure : {}", id);
        return centerStructureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CenterStructure : {}", id);
        centerStructureRepository.deleteById(id);
    }
}
