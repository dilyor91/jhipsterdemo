import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './logo.reducer';

export const LogoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const logoEntity = useAppSelector(state => state.logo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="logoDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.logo.detail.title">Logo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{logoEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleApplicationApp.logo.name">Name</Translate>
            </span>
          </dt>
          <dd>{logoEntity.name}</dd>
          <dt>
            <span id="logoData">
              <Translate contentKey="jhipsterSampleApplicationApp.logo.logoData">Logo Data</Translate>
            </span>
          </dt>
          <dd>{logoEntity.logoData}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="jhipsterSampleApplicationApp.logo.status">Status</Translate>
            </span>
          </dt>
          <dd>{logoEntity.status ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/logo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/logo/${logoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LogoDetail;
