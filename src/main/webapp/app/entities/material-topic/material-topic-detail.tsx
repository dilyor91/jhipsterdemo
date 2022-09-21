import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './material-topic.reducer';

export const MaterialTopicDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materialTopicEntity = useAppSelector(state => state.materialTopic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materialTopicDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.detail.title">MaterialTopic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materialTopicEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{materialTopicEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{materialTopicEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{materialTopicEntity.titleKr}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.materialTopicLevel">Material Topic Level</Translate>
          </dt>
          <dd>{materialTopicEntity.materialTopicLevel ? materialTopicEntity.materialTopicLevel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/material-topic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/material-topic/${materialTopicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaterialTopicDetail;
