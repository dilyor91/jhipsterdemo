import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './our-history.reducer';

export const OurHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ourHistoryEntity = useAppSelector(state => state.ourHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ourHistoryDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.ourHistory.detail.title">OurHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ourHistoryEntity.id}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.ourHistory.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{ourHistoryEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.ourHistory.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{ourHistoryEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.ourHistory.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{ourHistoryEntity.contentKr}</dd>
          <dt>
            <span id="postedDate">
              <Translate contentKey="jhipsterSampleApplicationApp.ourHistory.postedDate">Posted Date</Translate>
            </span>
          </dt>
          <dd>
            {ourHistoryEntity.postedDate ? (
              <TextFormat value={ourHistoryEntity.postedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/our-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/our-history/${ourHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OurHistoryDetail;
