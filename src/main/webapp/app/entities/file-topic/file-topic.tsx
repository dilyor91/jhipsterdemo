import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFileTopic } from 'app/shared/model/file-topic.model';
import { getEntities } from './file-topic.reducer';

export const FileTopic = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const fileTopicList = useAppSelector(state => state.fileTopic.entities);
  const loading = useAppSelector(state => state.fileTopic.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="file-topic-heading" data-cy="FileTopicHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.home.title">File Topics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/file-topic/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.home.createLabel">Create new File Topic</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fileTopicList && fileTopicList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileOrginalName">File Orginal Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileNameUz">File Name Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileNameRu">File Name Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileNameKr">File Name Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileType">File Type</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileSize">File Size</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.filePath">File Path</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.materialTopicLevel">Material Topic Level</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileTopicList.map((fileTopic, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/file-topic/${fileTopic.id}`} color="link" size="sm">
                      {fileTopic.id}
                    </Button>
                  </td>
                  <td>{fileTopic.fileOrginalName}</td>
                  <td>{fileTopic.fileNameUz}</td>
                  <td>{fileTopic.fileNameRu}</td>
                  <td>{fileTopic.fileNameKr}</td>
                  <td>{fileTopic.fileType}</td>
                  <td>{fileTopic.fileSize}</td>
                  <td>{fileTopic.filePath}</td>
                  <td>
                    {fileTopic.materialTopicLevel ? (
                      <Link to={`/material-topic-level/${fileTopic.materialTopicLevel.id}`}>{fileTopic.materialTopicLevel.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/file-topic/${fileTopic.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/file-topic/${fileTopic.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/file-topic/${fileTopic.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.home.notFound">No File Topics found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FileTopic;
