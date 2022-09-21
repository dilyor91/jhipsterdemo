import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './study-at-korea.reducer';

export const StudyAtKoreaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studyAtKoreaEntity = useAppSelector(state => state.studyAtKorea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studyAtKoreaDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.detail.title">StudyAtKorea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{studyAtKoreaEntity.contentKr}</dd>
        </dl>
        <Button tag={Link} to="/study-at-korea" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/study-at-korea/${studyAtKoreaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudyAtKoreaDetail;
