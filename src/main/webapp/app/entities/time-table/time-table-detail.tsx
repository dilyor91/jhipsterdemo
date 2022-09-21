import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './time-table.reducer';

export const TimeTableDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const timeTableEntity = useAppSelector(state => state.timeTable.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="timeTableDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.timeTable.detail.title">TimeTable</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.contentKr}</dd>
          <dt>
            <span id="postedDate">
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.postedDate">Posted Date</Translate>
            </span>
          </dt>
          <dd>
            {timeTableEntity.postedDate ? (
              <TextFormat value={timeTableEntity.postedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/time-table" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/time-table/${timeTableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TimeTableDetail;
