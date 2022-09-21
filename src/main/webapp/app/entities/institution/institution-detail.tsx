import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './institution.reducer';

export const InstitutionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const institutionEntity = useAppSelector(state => state.institution.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="institutionDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.institution.detail.title">Institution</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.id}</dd>
          <dt>
            <span id="institutionType">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.institutionType">Institution Type</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.institutionType}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.contentKr}</dd>
          <dt>
            <span id="logoName">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.logoName">Logo Name</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.logoName}</dd>
          <dt>
            <span id="logoData">
              <Translate contentKey="jhipsterSampleApplicationApp.institution.logoData">Logo Data</Translate>
            </span>
          </dt>
          <dd>{institutionEntity.logoData}</dd>
        </dl>
        <Button tag={Link} to="/institution" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/institution/${institutionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InstitutionDetail;
