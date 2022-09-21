import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banner.reducer';

export const BannerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bannerEntity = useAppSelector(state => state.banner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bannerDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.banner.detail.title">Banner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleApplicationApp.banner.name">Name</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.name}</dd>
          <dt>
            <span id="bannerData">
              <Translate contentKey="jhipsterSampleApplicationApp.banner.bannerData">Banner Data</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.bannerData}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="jhipsterSampleApplicationApp.banner.status">Status</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.status ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/banner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banner/${bannerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BannerDetail;
