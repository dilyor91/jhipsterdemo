import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITimeTable } from 'app/shared/model/time-table.model';
import { getEntity, updateEntity, createEntity, reset } from './time-table.reducer';

export const TimeTableUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const timeTableEntity = useAppSelector(state => state.timeTable.entity);
  const loading = useAppSelector(state => state.timeTable.loading);
  const updating = useAppSelector(state => state.timeTable.updating);
  const updateSuccess = useAppSelector(state => state.timeTable.updateSuccess);

  const handleClose = () => {
    navigate('/time-table');
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
      ...timeTableEntity,
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
          ...timeTableEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.timeTable.home.createOrEditLabel" data-cy="TimeTableCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.timeTable.home.createOrEditLabel">Create or edit a TimeTable</Translate>
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
                  id="time-table-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.titleUz')}
                id="time-table-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.titleRu')}
                id="time-table-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.titleKr')}
                id="time-table-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.contentUz')}
                id="time-table-contentUz"
                name="contentUz"
                data-cy="contentUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.contentRu')}
                id="time-table-contentRu"
                name="contentRu"
                data-cy="contentRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.contentKr')}
                id="time-table-contentKr"
                name="contentKr"
                data-cy="contentKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.timeTable.postedDate')}
                id="time-table-postedDate"
                name="postedDate"
                data-cy="postedDate"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/time-table" replace color="info">
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

export default TimeTableUpdate;
