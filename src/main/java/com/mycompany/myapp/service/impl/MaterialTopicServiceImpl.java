package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.MaterialTopic;
import com.mycompany.myapp.repository.MaterialTopicRepository;
import com.mycompany.myapp.service.MaterialTopicService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MaterialTopic}.
 */
@Service
@Transactional
public class MaterialTopicServiceImpl implements MaterialTopicService {

    private final Logger log = LoggerFactory.getLogger(MaterialTopicServiceImpl.class);

    private final MaterialTopicRepository materialTopicRepository;

    public MaterialTopicServiceImpl(MaterialTopicRepository materialTopicRepository) {
        this.materialTopicRepository = materialTopicRepository;
    }

    @Override
    public MaterialTopic save(MaterialTopic materialTopic) {
        log.debug("Request to save MaterialTopic : {}", materialTopic);
        return materialTopicRepository.save(materialTopic);
    }

    @Override
    public MaterialTopic update(MaterialTopic materialTopic) {
        log.debug("Request to update MaterialTopic : {}", materialTopic);
        return materialTopicRepository.save(materialTopic);
    }

    @Override
    public Optional<MaterialTopic> partialUpdate(MaterialTopic materialTopic) {
        log.debug("Request to partially update MaterialTopic : {}", materialTopic);

        return materialTopicRepository
            .findById(materialTopic.getId())
            .map(existingMaterialTopic -> {
                if (materialTopic.getTitleUz() != null) {
                    existingMaterialTopic.setTitleUz(materialTopic.getTitleUz());
                }
                if (materialTopic.getTitleRu() != null) {
                    existingMaterialTopic.setTitleRu(materialTopic.getTitleRu());
                }
                if (materialTopic.getTitleKr() != null) {
                    existingMaterialTopic.setTitleKr(materialTopic.getTitleKr());
                }

                return existingMaterialTopic;
            })
            .map(materialTopicRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialTopic> findAll() {
        log.debug("Request to get all MaterialTopics");
        return materialTopicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialTopic> findOne(Long id) {
        log.debug("Request to get MaterialTopic : {}", id);
        return materialTopicRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialTopic : {}", id);
        materialTopicRepository.deleteById(id);
    }
}
