package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Album;
import com.mycompany.myapp.repository.AlbumRepository;
import com.mycompany.myapp.service.AlbumService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Album}.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album save(Album album) {
        log.debug("Request to save Album : {}", album);
        return albumRepository.save(album);
    }

    @Override
    public Album update(Album album) {
        log.debug("Request to update Album : {}", album);
        return albumRepository.save(album);
    }

    @Override
    public Optional<Album> partialUpdate(Album album) {
        log.debug("Request to partially update Album : {}", album);

        return albumRepository
            .findById(album.getId())
            .map(existingAlbum -> {
                if (album.getTitleUz() != null) {
                    existingAlbum.setTitleUz(album.getTitleUz());
                }
                if (album.getTitleRu() != null) {
                    existingAlbum.setTitleRu(album.getTitleRu());
                }
                if (album.getTitleKr() != null) {
                    existingAlbum.setTitleKr(album.getTitleKr());
                }

                return existingAlbum;
            })
            .map(albumRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Album> findAll() {
        log.debug("Request to get all Albums");
        return albumRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Album> findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        return albumRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.deleteById(id);
    }
}
