import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudyAtKorea } from 'app/shared/model/study-at-korea.model';
import { getEntities } from './study-at-korea.reducer';

export const StudyAtKorea = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const studyAtKoreaList = useAppSelector(state => state.studyAtKorea.entities);
  const loading = useAppSelector(state => state.studyAtKorea.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="study-at-korea-heading" data-cy="StudyAtKoreaHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.home.title">Study At Koreas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/study-at-korea/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.home.createLabel">Create new Study At Korea</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {studyAtKoreaList && studyAtKoreaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.contentUz">Content Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.contentRu">Content Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.contentKr">Content Kr</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studyAtKoreaList.map((studyAtKorea, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/study-at-korea/${studyAtKorea.id}`} color="link" size="sm">
                      {studyAtKorea.id}
                    </Button>
                  </td>
                  <td>{studyAtKorea.titleUz}</td>
                  <td>{studyAtKorea.titleRu}</td>
                  <td>{studyAtKorea.titleKr}</td>
                  <td>{studyAtKorea.contentUz}</td>
                  <td>{studyAtKorea.contentRu}</td>
                  <td>{studyAtKorea.contentKr}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/study-at-korea/${studyAtKorea.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/study-at-korea/${studyAtKorea.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/study-at-korea/${studyAtKorea.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.home.notFound">No Study At Koreas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StudyAtKorea;
