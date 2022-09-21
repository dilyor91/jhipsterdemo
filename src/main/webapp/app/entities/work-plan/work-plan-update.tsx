import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkPlan } from 'app/shared/model/work-plan.model';
import { PlanType } from 'app/shared/model/enumerations/plan-type.model';
import { getEntity, updateEntity, createEntity, reset } from './work-plan.reducer';

export const WorkPlanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workPlanEntity = useAppSelector(state => state.workPlan.entity);
  const loading = useAppSelector(state => state.workPlan.loading);
  const updating = useAppSelector(state => state.workPlan.updating);
  const updateSuccess = useAppSelector(state => state.workPlan.updateSuccess);
  const planTypeValues = Object.keys(PlanType);

  const handleClose = () => {
    navigate('/work-plan');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...workPlanEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          planType: 'MONTH',
          ...workPlanEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.workPlan.home.createOrEditLabel" data-cy="WorkPlanCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.workPlan.home.createOrEditLabel">Create or edit a WorkPlan</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="work-plan-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.titleUz')}
                id="work-plan-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.titleRu')}
                id="work-plan-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.titleKr')}
                id="work-plan-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.contentUz')}
                id="work-plan-contentUz"
                name="contentUz"
                data-cy="contentUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.contentRu')}
                id="work-plan-contentRu"
                name="contentRu"
                data-cy="contentRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.contentKr')}
                id="work-plan-contentKr"
                name="contentKr"
                data-cy="contentKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.workPlan.planType')}
                id="work-plan-planType"
                name="planType"
                data-cy="planType"
                type="select"
              >
                {planTypeValues.map(planType => (
                  <option value={planType} key={planType}>
                    {translate('jhipsterSampleApplicationApp.PlanType.' + planType)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/work-plan" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WorkPlanUpdate;
