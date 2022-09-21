import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './file.reducer';

export const FileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fileEntity = useAppSelector(state => state.file.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fileDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.file.detail.title">File</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fileEntity.id}</dd>
          <dt>
            <span id="orginalName">
              <Translate contentKey="jhipsterSampleApplicationApp.file.orginalName">Orginal Name</Translate>
            </span>
          </dt>
          <dd>{fileEntity.orginalName}</dd>
          <dt>
            <span id="fileName">
              <Translate contentKey="jhipsterSampleApplicationApp.file.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{fileEntity.fileName}</dd>
          <dt>
            <span id="fileSize">
              <Translate contentKey="jhipsterSampleApplicationApp.file.fileSize">File Size</Translate>
            </span>
          </dt>
          <dd>{fileEntity.fileSize}</dd>
          <dt>
            <span id="fileFormat">
              <Translate contentKey="jhipsterSampleApplicationApp.file.fileFormat">File Format</Translate>
            </span>
          </dt>
          <dd>{fileEntity.fileFormat}</dd>
          <dt>
            <span id="filePath">
              <Translate contentKey="jhipsterSampleApplicationApp.file.filePath">File Path</Translate>
            </span>
          </dt>
          <dd>{fileEntity.filePath}</dd>
          <dt>
            <span id="fileEntity">
              <Translate contentKey="jhipsterSampleApplicationApp.file.fileEntity">File Entity</Translate>
            </span>
          </dt>
          <dd>{fileEntity.fileEntity}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.file.institution">Institution</Translate>
          </dt>
          <dd>{fileEntity.institution ? fileEntity.institution.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.file.studyAtKorea">Study At Korea</Translate>
          </dt>
          <dd>{fileEntity.studyAtKorea ? fileEntity.studyAtKorea.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file/${fileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FileDetail;
