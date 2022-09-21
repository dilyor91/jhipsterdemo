import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './file-topic.reducer';

export const FileTopicDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fileTopicEntity = useAppSelector(state => state.fileTopic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fileTopicDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.detail.title">FileTopic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.id}</dd>
          <dt>
            <span id="fileOrginalName">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileOrginalName">File Orginal Name</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.fileOrginalName}</dd>
          <dt>
            <span id="fileNameUz">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileNameUz">File Name Uz</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.fileNameUz}</dd>
          <dt>
            <span id="fileNameRu">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileNameRu">File Name Ru</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.fileNameRu}</dd>
          <dt>
            <span id="fileNameKr">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileNameKr">File Name Kr</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.fileNameKr}</dd>
          <dt>
            <span id="fileType">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileType">File Type</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.fileType}</dd>
          <dt>
            <span id="fileSize">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.fileSize">File Size</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.fileSize}</dd>
          <dt>
            <span id="filePath">
              <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.filePath">File Path</Translate>
            </span>
          </dt>
          <dd>{fileTopicEntity.filePath}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.materialTopicLevel">Material Topic Level</Translate>
          </dt>
          <dd>{fileTopicEntity.materialTopicLevel ? fileTopicEntity.materialTopicLevel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/file-topic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file-topic/${fileTopicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FileTopicDetail;
