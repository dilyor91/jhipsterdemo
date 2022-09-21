package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Image;
import com.mycompany.myapp.repository.ImageRepository;
import com.mycompany.myapp.service.ImageService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Image}.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image save(Image image) {
        log.debug("Request to save Image : {}", image);
        return imageRepository.save(image);
    }

    @Override
    public Image update(Image image) {
        log.debug("Request to update Image : {}", image);
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> partialUpdate(Image image) {
        log.debug("Request to partially update Image : {}", image);

        return imageRepository
            .findById(image.getId())
            .map(existingImage -> {
                if (image.getOrginalName() != null) {
                    existingImage.setOrginalName(image.getOrginalName());
                }
                if (image.getName() != null) {
                    existingImage.setName(image.getName());
                }
                if (image.getImageData() != null) {
                    existingImage.setImageData(image.getImageData());
                }
                if (image.getMainlyPhoto() != null) {
                    existingImage.setMainlyPhoto(image.getMainlyPhoto());
                }

                return existingImage;
            })
            .map(imageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Image> findAll() {
        log.debug("Request to get all Images");
        return imageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Image> findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        return imageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.deleteById(id);
    }
}
