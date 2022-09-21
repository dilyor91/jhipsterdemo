import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMaterialTopic } from 'app/shared/model/material-topic.model';
import { getEntities } from './material-topic.reducer';

export const MaterialTopic = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const materialTopicList = useAppSelector(state => state.materialTopic.entities);
  const loading = useAppSelector(state => state.materialTopic.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="material-topic-heading" data-cy="MaterialTopicHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.home.title">Material Topics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/material-topic/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.home.createLabel">Create new Material Topic</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {materialTopicList && materialTopicList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.materialTopicLevel">Material Topic Level</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materialTopicList.map((materialTopic, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/material-topic/${materialTopic.id}`} color="link" size="sm">
                      {materialTopic.id}
                    </Button>
                  </td>
                  <td>{materialTopic.titleUz}</td>
                  <td>{materialTopic.titleRu}</td>
                  <td>{materialTopic.titleKr}</td>
                  <td>
                    {materialTopic.materialTopicLevel ? (
                      <Link to={`/material-topic-level/${materialTopic.materialTopicLevel.id}`}>{materialTopic.materialTopicLevel.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/material-topic/${materialTopic.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/material-topic/${materialTopic.id}/edit`}
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
                        to={`/material-topic/${materialTopic.id}/delete`}
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
              <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.home.notFound">No Material Topics found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MaterialTopic;
