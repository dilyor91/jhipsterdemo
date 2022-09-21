package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Institution;
import com.mycompany.myapp.repository.InstitutionRepository;
import com.mycompany.myapp.service.InstitutionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Institution}.
 */
@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private final Logger log = LoggerFactory.getLogger(InstitutionServiceImpl.class);

    private final InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public Institution save(Institution institution) {
        log.debug("Request to save Institution : {}", institution);
        return institutionRepository.save(institution);
    }

    @Override
    public Institution update(Institution institution) {
        log.debug("Request to update Institution : {}", institution);
        return institutionRepository.save(institution);
    }

    @Override
    public Optional<Institution> partialUpdate(Institution institution) {
        log.debug("Request to partially update Institution : {}", institution);

        return institutionRepository
            .findById(institution.getId())
            .map(existingInstitution -> {
                if (institution.getInstitutionType() != null) {
                    existingInstitution.setInstitutionType(institution.getInstitutionType());
                }
                if (institution.getTitleUz() != null) {
                    existingInstitution.setTitleUz(institution.getTitleUz());
                }
                if (institution.getTitleRu() != null) {
                    existingInstitution.setTitleRu(institution.getTitleRu());
                }
                if (institution.getTitleKr() != null) {
                    existingInstitution.setTitleKr(institution.getTitleKr());
                }
                if (institution.getContentUz() != null) {
                    existingInstitution.setContentUz(institution.getContentUz());
                }
                if (institution.getContentRu() != null) {
                    existingInstitution.setContentRu(institution.getContentRu());
                }
                if (institution.getContentKr() != null) {
                    existingInstitution.setContentKr(institution.getContentKr());
                }
                if (institution.getLogoName() != null) {
                    existingInstitution.setLogoName(institution.getLogoName());
                }
                if (institution.getLogoData() != null) {
                    existingInstitution.setLogoData(institution.getLogoData());
                }

                return existingInstitution;
            })
            .map(institutionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Institution> findAll() {
        log.debug("Request to get all Institutions");
        return institutionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Institution> findOne(Long id) {
        log.debug("Request to get Institution : {}", id);
        return institutionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Institution : {}", id);
        institutionRepository.deleteById(id);
    }
}
