import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './work-plan.reducer';

export const WorkPlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workPlanEntity = useAppSelector(state => state.workPlan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workPlanDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.workPlan.detail.title">WorkPlan</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.id}</dd>
          <dt>
            <span id="titleUz">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.titleUz">Title Uz</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.titleUz}</dd>
          <dt>
            <span id="titleRu">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.titleRu">Title Ru</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.titleRu}</dd>
          <dt>
            <span id="titleKr">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.titleKr">Title Kr</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.titleKr}</dd>
          <dt>
            <span id="contentUz">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.contentUz">Content Uz</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.contentUz}</dd>
          <dt>
            <span id="contentRu">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.contentRu">Content Ru</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.contentRu}</dd>
          <dt>
            <span id="contentKr">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.contentKr">Content Kr</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.contentKr}</dd>
          <dt>
            <span id="planType">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.planType">Plan Type</Translate>
            </span>
          </dt>
          <dd>{workPlanEntity.planType}</dd>
        </dl>
        <Button tag={Link} to="/work-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/work-plan/${workPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkPlanDetail;
