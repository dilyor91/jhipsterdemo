package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.News;
import com.mycompany.myapp.repository.NewsRepository;
import com.mycompany.myapp.service.NewsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link News}.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News save(News news) {
        log.debug("Request to save News : {}", news);
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        log.debug("Request to update News : {}", news);
        return newsRepository.save(news);
    }

    @Override
    public Optional<News> partialUpdate(News news) {
        log.debug("Request to partially update News : {}", news);

        return newsRepository
            .findById(news.getId())
            .map(existingNews -> {
                if (news.getTitleUz() != null) {
                    existingNews.setTitleUz(news.getTitleUz());
                }
                if (news.getTitleRu() != null) {
                    existingNews.setTitleRu(news.getTitleRu());
                }
                if (news.getTitleKr() != null) {
                    existingNews.setTitleKr(news.getTitleKr());
                }
                if (news.getContentUz() != null) {
                    existingNews.setContentUz(news.getContentUz());
                }
                if (news.getContentRu() != null) {
                    existingNews.setContentRu(news.getContentRu());
                }
                if (news.getContentKr() != null) {
                    existingNews.setContentKr(news.getContentKr());
                }
                if (news.getPostedDate() != null) {
                    existingNews.setPostedDate(news.getPostedDate());
                }
                if (news.getStatus() != null) {
                    existingNews.setStatus(news.getStatus());
                }

                return existingNews;
            })
            .map(newsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<News> findAll() {
        log.debug("Request to get all News");
        return newsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<News> findOne(Long id) {
        log.debug("Request to get News : {}", id);
        return newsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.deleteById(id);
    }
}
