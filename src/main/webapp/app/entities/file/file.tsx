import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFile } from 'app/shared/model/file.model';
import { getEntities } from './file.reducer';

export const File = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const fileList = useAppSelector(state => state.file.entities);
  const loading = useAppSelector(state => state.file.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="file-heading" data-cy="FileHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.file.home.title">Files</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.file.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/file/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.file.home.createLabel">Create new File</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fileList && fileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.orginalName">Orginal Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.fileName">File Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.fileSize">File Size</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.fileFormat">File Format</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.filePath">File Path</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.fileEntity">File Entity</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.institution">Institution</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.file.studyAtKorea">Study At Korea</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileList.map((file, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/file/${file.id}`} color="link" size="sm">
                      {file.id}
                    </Button>
                  </td>
                  <td>{file.orginalName}</td>
                  <td>{file.fileName}</td>
                  <td>{file.fileSize}</td>
                  <td>{file.fileFormat}</td>
                  <td>{file.filePath}</td>
                  <td>
                    <Translate contentKey={`jhipsterSampleApplicationApp.FileEntity.${file.fileEntity}`} />
                  </td>
                  <td>{file.institution ? <Link to={`/institution/${file.institution.id}`}>{file.institution.id}</Link> : ''}</td>
                  <td>{file.studyAtKorea ? <Link to={`/study-at-korea/${file.studyAtKorea.id}`}>{file.studyAtKorea.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/file/${file.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/file/${file.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/file/${file.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.file.home.notFound">No Files found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default File;
