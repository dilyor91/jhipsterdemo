import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkPlan } from 'app/shared/model/work-plan.model';
import { getEntities } from './work-plan.reducer';

export const WorkPlan = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const workPlanList = useAppSelector(state => state.workPlan.entities);
  const loading = useAppSelector(state => state.workPlan.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="work-plan-heading" data-cy="WorkPlanHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.workPlan.home.title">Work Plans</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.workPlan.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/work-plan/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.workPlan.home.createLabel">Create new Work Plan</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {workPlanList && workPlanList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.contentUz">Content Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.contentRu">Content Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.contentKr">Content Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.workPlan.planType">Plan Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workPlanList.map((workPlan, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/work-plan/${workPlan.id}`} color="link" size="sm">
                      {workPlan.id}
                    </Button>
                  </td>
                  <td>{workPlan.titleUz}</td>
                  <td>{workPlan.titleRu}</td>
                  <td>{workPlan.titleKr}</td>
                  <td>{workPlan.contentUz}</td>
                  <td>{workPlan.contentRu}</td>
                  <td>{workPlan.contentKr}</td>
                  <td>
                    <Translate contentKey={`jhipsterSampleApplicationApp.PlanType.${workPlan.planType}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/work-plan/${workPlan.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/work-plan/${workPlan.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/work-plan/${workPlan.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.workPlan.home.notFound">No Work Plans found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default WorkPlan;
