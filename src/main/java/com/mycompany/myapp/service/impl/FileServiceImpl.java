package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.File;
import com.mycompany.myapp.repository.FileRepository;
import com.mycompany.myapp.service.FileService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link File}.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        return fileRepository.save(file);
    }

    @Override
    public File update(File file) {
        log.debug("Request to update File : {}", file);
        return fileRepository.save(file);
    }

    @Override
    public Optional<File> partialUpdate(File file) {
        log.debug("Request to partially update File : {}", file);

        return fileRepository
            .findById(file.getId())
            .map(existingFile -> {
                if (file.getOrginalName() != null) {
                    existingFile.setOrginalName(file.getOrginalName());
                }
                if (file.getFileName() != null) {
                    existingFile.setFileName(file.getFileName());
                }
                if (file.getFileSize() != null) {
                    existingFile.setFileSize(file.getFileSize());
                }
                if (file.getFileFormat() != null) {
                    existingFile.setFileFormat(file.getFileFormat());
                }
                if (file.getFilePath() != null) {
                    existingFile.setFilePath(file.getFilePath());
                }
                if (file.getFileEntity() != null) {
                    existingFile.setFileEntity(file.getFileEntity());
                }

                return existingFile;
            })
            .map(fileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<File> findAll() {
        log.debug("Request to get all Files");
        return fileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<File> findOne(Long id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
