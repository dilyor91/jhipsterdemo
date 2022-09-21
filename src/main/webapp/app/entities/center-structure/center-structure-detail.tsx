import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './center-structure.reducer';

export const CenterStructureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const centerStructureEntity = useAppSelector(state => state.centerStructure.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="centerStructureDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.centerStructure.detail.title">CenterStructure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{centerStructureEntity.id}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.centerStructure.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{centerStructureEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.centerStructure.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{centerStructureEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.centerStructure.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{centerStructureEntity.contentKr}</dd>
        </dl>
        <Button tag={Link} to="/center-structure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/center-structure/${centerStructureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CenterStructureDetail;
