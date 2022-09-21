import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEvents } from 'app/shared/model/events.model';
import { getEntity, updateEntity, createEntity, reset } from './events.reducer';

export const EventsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const eventsEntity = useAppSelector(state => state.events.entity);
  const loading = useAppSelector(state => state.events.loading);
  const updating = useAppSelector(state => state.events.updating);
  const updateSuccess = useAppSelector(state => state.events.updateSuccess);

  const handleClose = () => {
    navigate('/events');
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
      ...eventsEntity,
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
          ...eventsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.events.home.createOrEditLabel" data-cy="EventsCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.events.home.createOrEditLabel">Create or edit a Events</Translate>
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
                  id="events-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.titleUz')}
                id="events-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.titleRu')}
                id="events-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.titleKr')}
                id="events-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.contentUz')}
                id="events-contentUz"
                name="contentUz"
                data-cy="contentUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.contentRu')}
                id="events-contentRu"
                name="contentRu"
                data-cy="contentRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.contentKr')}
                id="events-contentKr"
                name="contentKr"
                data-cy="contentKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.postedDate')}
                id="events-postedDate"
                name="postedDate"
                data-cy="postedDate"
                type="date"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.events.status')}
                id="events-status"
                name="status"
                data-cy="status"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/events" replace color="info">
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

export default EventsUpdate;
