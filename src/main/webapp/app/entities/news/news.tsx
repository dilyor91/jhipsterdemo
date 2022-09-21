import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INews } from 'app/shared/model/news.model';
import { getEntities } from './news.reducer';

export const News = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const newsList = useAppSelector(state => state.news.entities);
  const loading = useAppSelector(state => state.news.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="news-heading" data-cy="NewsHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.news.home.title">News</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.news.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/news/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.news.home.createLabel">Create new News</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {newsList && newsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.contentUz">Content Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.contentRu">Content Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.contentKr">Content Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.postedDate">Posted Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.news.status">Status</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {newsList.map((news, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/news/${news.id}`} color="link" size="sm">
                      {news.id}
                    </Button>
                  </td>
                  <td>{news.titleUz}</td>
                  <td>{news.titleRu}</td>
                  <td>{news.titleKr}</td>
                  <td>{news.contentUz}</td>
                  <td>{news.contentRu}</td>
                  <td>{news.contentKr}</td>
                  <td>{news.postedDate ? <TextFormat type="date" value={news.postedDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{news.status ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/news/${news.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/news/${news.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/news/${news.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.news.home.notFound">No News found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default News;
