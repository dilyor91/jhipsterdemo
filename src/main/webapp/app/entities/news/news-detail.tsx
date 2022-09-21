import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './news.reducer';

export const NewsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const newsEntity = useAppSelector(state => state.news.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="newsDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.news.detail.title">News</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{newsEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.news.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{newsEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.news.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{newsEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.news.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{newsEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.news.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{newsEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.news.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{newsEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.news.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{newsEntity.contentKr}</dd>
          <dt>
            <span id="postedDate">
              <Translate contentKey="jhipsterSampleApplicationApp.news.postedDate">Posted Date</Translate>
            </span>
          </dt>
          <dd>{newsEntity.postedDate ? <TextFormat value={newsEntity.postedDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="jhipsterSampleApplicationApp.news.status">Status</Translate>
            </span>
          </dt>
          <dd>{newsEntity.status ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/news" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/news/${newsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NewsDetail;
