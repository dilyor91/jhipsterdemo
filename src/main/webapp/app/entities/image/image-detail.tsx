import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './image.reducer';

export const ImageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const imageEntity = useAppSelector(state => state.image.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imageDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.image.detail.title">Image</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imageEntity.id}</dd>
          <dt>
            <span id="orginalName">
              <Translate contentKey="jhipsterSampleApplicationApp.image.orginalName">Orginal Name</Translate>
            </span>
          </dt>
          <dd>{imageEntity.orginalName}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleApplicationApp.image.name">Name</Translate>
            </span>
          </dt>
          <dd>{imageEntity.name}</dd>
          <dt>
            <span id="imageData">
              <Translate contentKey="jhipsterSampleApplicationApp.image.imageData">Image Data</Translate>
            </span>
          </dt>
          <dd>{imageEntity.imageData}</dd>
          <dt>
            <span id="mainlyPhoto">
              <Translate contentKey="jhipsterSampleApplicationApp.image.mainlyPhoto">Mainly Photo</Translate>
            </span>
          </dt>
          <dd>{imageEntity.mainlyPhoto ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.image.image">Image</Translate>
          </dt>
          <dd>{imageEntity.image ? imageEntity.image.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/image" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/image/${imageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImageDetail;
