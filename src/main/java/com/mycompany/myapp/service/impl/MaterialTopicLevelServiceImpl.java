package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.MaterialTopicLevel;
import com.mycompany.myapp.repository.MaterialTopicLevelRepository;
import com.mycompany.myapp.service.MaterialTopicLevelService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MaterialTopicLevel}.
 */
@Service
@Transactional
public class MaterialTopicLevelServiceImpl implements MaterialTopicLevelService {

    private final Logger log = LoggerFactory.getLogger(MaterialTopicLevelServiceImpl.class);

    private final MaterialTopicLevelRepository materialTopicLevelRepository;

    public MaterialTopicLevelServiceImpl(MaterialTopicLevelRepository materialTopicLevelRepository) {
        this.materialTopicLevelRepository = materialTopicLevelRepository;
    }

    @Override
    public MaterialTopicLevel save(MaterialTopicLevel materialTopicLevel) {
        log.debug("Request to save MaterialTopicLevel : {}", materialTopicLevel);
        return materialTopicLevelRepository.save(materialTopicLevel);
    }

    @Override
    public MaterialTopicLevel update(MaterialTopicLevel materialTopicLevel) {
        log.debug("Request to update MaterialTopicLevel : {}", materialTopicLevel);
        return materialTopicLevelRepository.save(materialTopicLevel);
    }

    @Override
    public Optional<MaterialTopicLevel> partialUpdate(MaterialTopicLevel materialTopicLevel) {
        log.debug("Request to partially update MaterialTopicLevel : {}", materialTopicLevel);

        return materialTopicLevelRepository
            .findById(materialTopicLevel.getId())
            .map(existingMaterialTopicLevel -> {
                if (materialTopicLevel.getTitleUz() != null) {
                    existingMaterialTopicLevel.setTitleUz(materialTopicLevel.getTitleUz());
                }
                if (materialTopicLevel.getTitleRu() != null) {
                    existingMaterialTopicLevel.setTitleRu(materialTopicLevel.getTitleRu());
                }
                if (materialTopicLevel.getTitleKr() != null) {
                    existingMaterialTopicLevel.setTitleKr(materialTopicLevel.getTitleKr());
                }

                return existingMaterialTopicLevel;
            })
            .map(materialTopicLevelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialTopicLevel> findAll() {
        log.debug("Request to get all MaterialTopicLevels");
        return materialTopicLevelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialTopicLevel> findOne(Long id) {
        log.debug("Request to get MaterialTopicLevel : {}", id);
        return materialTopicLevelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialTopicLevel : {}", id);
        materialTopicLevelRepository.deleteById(id);
    }
}
