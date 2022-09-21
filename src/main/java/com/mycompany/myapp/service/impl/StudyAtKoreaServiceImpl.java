package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.StudyAtKorea;
import com.mycompany.myapp.repository.StudyAtKoreaRepository;
import com.mycompany.myapp.service.StudyAtKoreaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StudyAtKorea}.
 */
@Service
@Transactional
public class StudyAtKoreaServiceImpl implements StudyAtKoreaService {

    private final Logger log = LoggerFactory.getLogger(StudyAtKoreaServiceImpl.class);

    private final StudyAtKoreaRepository studyAtKoreaRepository;

    public StudyAtKoreaServiceImpl(StudyAtKoreaRepository studyAtKoreaRepository) {
        this.studyAtKoreaRepository = studyAtKoreaRepository;
    }

    @Override
    public StudyAtKorea save(StudyAtKorea studyAtKorea) {
        log.debug("Request to save StudyAtKorea : {}", studyAtKorea);
        return studyAtKoreaRepository.save(studyAtKorea);
    }

    @Override
    public StudyAtKorea update(StudyAtKorea studyAtKorea) {
        log.debug("Request to update StudyAtKorea : {}", studyAtKorea);
        return studyAtKoreaRepository.save(studyAtKorea);
    }

    @Override
    public Optional<StudyAtKorea> partialUpdate(StudyAtKorea studyAtKorea) {
        log.debug("Request to partially update StudyAtKorea : {}", studyAtKorea);

        return studyAtKoreaRepository
            .findById(studyAtKorea.getId())
            .map(existingStudyAtKorea -> {
                if (studyAtKorea.getTitleUz() != null) {
                    existingStudyAtKorea.setTitleUz(studyAtKorea.getTitleUz());
                }
                if (studyAtKorea.getTitleRu() != null) {
                    existingStudyAtKorea.setTitleRu(studyAtKorea.getTitleRu());
                }
                if (studyAtKorea.getTitleKr() != null) {
                    existingStudyAtKorea.setTitleKr(studyAtKorea.getTitleKr());
                }
                if (studyAtKorea.getContentUz() != null) {
                    existingStudyAtKorea.setContentUz(studyAtKorea.getContentUz());
                }
                if (studyAtKorea.getContentRu() != null) {
                    existingStudyAtKorea.setContentRu(studyAtKorea.getContentRu());
                }
                if (studyAtKorea.getContentKr() != null) {
                    existingStudyAtKorea.setContentKr(studyAtKorea.getContentKr());
                }

                return existingStudyAtKorea;
            })
            .map(studyAtKoreaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudyAtKorea> findAll() {
        log.debug("Request to get all StudyAtKoreas");
        return studyAtKoreaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudyAtKorea> findOne(Long id) {
        log.debug("Request to get StudyAtKorea : {}", id);
        return studyAtKoreaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudyAtKorea : {}", id);
        studyAtKoreaRepository.deleteById(id);
    }
}
