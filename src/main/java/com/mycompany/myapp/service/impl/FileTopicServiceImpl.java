package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FileTopic;
import com.mycompany.myapp.repository.FileTopicRepository;
import com.mycompany.myapp.service.FileTopicService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileTopic}.
 */
@Service
@Transactional
public class FileTopicServiceImpl implements FileTopicService {

    private final Logger log = LoggerFactory.getLogger(FileTopicServiceImpl.class);

    private final FileTopicRepository fileTopicRepository;

    public FileTopicServiceImpl(FileTopicRepository fileTopicRepository) {
        this.fileTopicRepository = fileTopicRepository;
    }

    @Override
    public FileTopic save(FileTopic fileTopic) {
        log.debug("Request to save FileTopic : {}", fileTopic);
        return fileTopicRepository.save(fileTopic);
    }

    @Override
    public FileTopic update(FileTopic fileTopic) {
        log.debug("Request to update FileTopic : {}", fileTopic);
        return fileTopicRepository.save(fileTopic);
    }

    @Override
    public Optional<FileTopic> partialUpdate(FileTopic fileTopic) {
        log.debug("Request to partially update FileTopic : {}", fileTopic);

        return fileTopicRepository
            .findById(fileTopic.getId())
            .map(existingFileTopic -> {
                if (fileTopic.getFileOrginalName() != null) {
                    existingFileTopic.setFileOrginalName(fileTopic.getFileOrginalName());
                }
                if (fileTopic.getFileNameUz() != null) {
                    existingFileTopic.setFileNameUz(fileTopic.getFileNameUz());
                }
                if (fileTopic.getFileNameRu() != null) {
                    existingFileTopic.setFileNameRu(fileTopic.getFileNameRu());
                }
                if (fileTopic.getFileNameKr() != null) {
                    existingFileTopic.setFileNameKr(fileTopic.getFileNameKr());
                }
                if (fileTopic.getFileType() != null) {
                    existingFileTopic.setFileType(fileTopic.getFileType());
                }
                if (fileTopic.getFileSize() != null) {
                    existingFileTopic.setFileSize(fileTopic.getFileSize());
                }
                if (fileTopic.getFilePath() != null) {
                    existingFileTopic.setFilePath(fileTopic.getFilePath());
                }

                return existingFileTopic;
            })
            .map(fileTopicRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileTopic> findAll() {
        log.debug("Request to get all FileTopics");
        return fileTopicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileTopic> findOne(Long id) {
        log.debug("Request to get FileTopic : {}", id);
        return fileTopicRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileTopic : {}", id);
        fileTopicRepository.deleteById(id);
    }
}
