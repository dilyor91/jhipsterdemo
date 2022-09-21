import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.address.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{addressEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.address.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{addressEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.address.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{addressEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.address.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{addressEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.address.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{addressEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.address.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{addressEntity.contentKr}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
