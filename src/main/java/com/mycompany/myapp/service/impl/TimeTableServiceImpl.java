package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TimeTable;
import com.mycompany.myapp.repository.TimeTableRepository;
import com.mycompany.myapp.service.TimeTableService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TimeTable}.
 */
@Service
@Transactional
public class TimeTableServiceImpl implements TimeTableService {

    private final Logger log = LoggerFactory.getLogger(TimeTableServiceImpl.class);

    private final TimeTableRepository timeTableRepository;

    public TimeTableServiceImpl(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    @Override
    public TimeTable save(TimeTable timeTable) {
        log.debug("Request to save TimeTable : {}", timeTable);
        return timeTableRepository.save(timeTable);
    }

    @Override
    public TimeTable update(TimeTable timeTable) {
        log.debug("Request to update TimeTable : {}", timeTable);
        return timeTableRepository.save(timeTable);
    }

    @Override
    public Optional<TimeTable> partialUpdate(TimeTable timeTable) {
        log.debug("Request to partially update TimeTable : {}", timeTable);

        return timeTableRepository
            .findById(timeTable.getId())
            .map(existingTimeTable -> {
                if (timeTable.getTitleUz() != null) {
                    existingTimeTable.setTitleUz(timeTable.getTitleUz());
                }
                if (timeTable.getTitleRu() != null) {
                    existingTimeTable.setTitleRu(timeTable.getTitleRu());
                }
                if (timeTable.getTitleKr() != null) {
                    existingTimeTable.setTitleKr(timeTable.getTitleKr());
                }
                if (timeTable.getContentUz() != null) {
                    existingTimeTable.setContentUz(timeTable.getContentUz());
                }
                if (timeTable.getContentRu() != null) {
                    existingTimeTable.setContentRu(timeTable.getContentRu());
                }
                if (timeTable.getContentKr() != null) {
                    existingTimeTable.setContentKr(timeTable.getContentKr());
                }
                if (timeTable.getPostedDate() != null) {
                    existingTimeTable.setPostedDate(timeTable.getPostedDate());
                }

                return existingTimeTable;
            })
            .map(timeTableRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeTable> findAll() {
        log.debug("Request to get all TimeTables");
        return timeTableRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeTable> findOne(Long id) {
        log.debug("Request to get TimeTable : {}", id);
        return timeTableRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeTable : {}", id);
        timeTableRepository.deleteById(id);
    }
}
