package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Banner;
import com.mycompany.myapp.repository.BannerRepository;
import com.mycompany.myapp.service.BannerService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Banner}.
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    private final Logger log = LoggerFactory.getLogger(BannerServiceImpl.class);

    private final BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Banner save(Banner banner) {
        log.debug("Request to save Banner : {}", banner);
        return bannerRepository.save(banner);
    }

    @Override
    public Banner update(Banner banner) {
        log.debug("Request to update Banner : {}", banner);
        return bannerRepository.save(banner);
    }

    @Override
    public Optional<Banner> partialUpdate(Banner banner) {
        log.debug("Request to partially update Banner : {}", banner);

        return bannerRepository
            .findById(banner.getId())
            .map(existingBanner -> {
                if (banner.getName() != null) {
                    existingBanner.setName(banner.getName());
                }
                if (banner.getBannerData() != null) {
                    existingBanner.setBannerData(banner.getBannerData());
                }
                if (banner.getStatus() != null) {
                    existingBanner.setStatus(banner.getStatus());
                }

                return existingBanner;
            })
            .map(bannerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Banner> findAll() {
        log.debug("Request to get all Banners");
        return bannerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Banner> findOne(Long id) {
        log.debug("Request to get Banner : {}", id);
        return bannerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banner : {}", id);
        bannerRepository.deleteById(id);
    }
}
