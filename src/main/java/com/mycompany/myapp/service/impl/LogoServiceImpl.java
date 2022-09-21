package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Logo;
import com.mycompany.myapp.repository.LogoRepository;
import com.mycompany.myapp.service.LogoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Logo}.
 */
@Service
@Transactional
public class LogoServiceImpl implements LogoService {

    private final Logger log = LoggerFactory.getLogger(LogoServiceImpl.class);

    private final LogoRepository logoRepository;

    public LogoServiceImpl(LogoRepository logoRepository) {
        this.logoRepository = logoRepository;
    }

    @Override
    public Logo save(Logo logo) {
        log.debug("Request to save Logo : {}", logo);
        return logoRepository.save(logo);
    }

    @Override
    public Logo update(Logo logo) {
        log.debug("Request to update Logo : {}", logo);
        return logoRepository.save(logo);
    }

    @Override
    public Optional<Logo> partialUpdate(Logo logo) {
        log.debug("Request to partially update Logo : {}", logo);

        return logoRepository
            .findById(logo.getId())
            .map(existingLogo -> {
                if (logo.getName() != null) {
                    existingLogo.setName(logo.getName());
                }
                if (logo.getLogoData() != null) {
                    existingLogo.setLogoData(logo.getLogoData());
                }
                if (logo.getStatus() != null) {
                    existingLogo.setStatus(logo.getStatus());
                }

                return existingLogo;
            })
            .map(logoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Logo> findAll() {
        log.debug("Request to get all Logos");
        return logoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Logo> findOne(Long id) {
        log.debug("Request to get Logo : {}", id);
        return logoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Logo : {}", id);
        logoRepository.deleteById(id);
    }
}
