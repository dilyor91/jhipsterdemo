import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './events.reducer';

export const EventsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eventsEntity = useAppSelector(state => state.events.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventsDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.events.detail.title">Events</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.events.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.events.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.events.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.events.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.events.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.events.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.contentKr}</dd>
          <dt>
            <span id="postedDate">
              <Translate contentKey="jhipsterSampleApplicationApp.events.postedDate">Posted Date</Translate>
            </span>
          </dt>
          <dd>
            {eventsEntity.postedDate ? <TextFormat value={eventsEntity.postedDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="jhipsterSampleApplicationApp.events.status">Status</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.status ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/events" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/events/${eventsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventsDetail;
