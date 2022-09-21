import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMaterialTopicLevel } from 'app/shared/model/material-topic-level.model';
import { getEntity, updateEntity, createEntity, reset } from './material-topic-level.reducer';

export const MaterialTopicLevelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const materialTopicLevelEntity = useAppSelector(state => state.materialTopicLevel.entity);
  const loading = useAppSelector(state => state.materialTopicLevel.loading);
  const updating = useAppSelector(state => state.materialTopicLevel.updating);
  const updateSuccess = useAppSelector(state => state.materialTopicLevel.updateSuccess);

  const handleClose = () => {
    navigate('/material-topic-level');
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
      ...materialTopicLevelEntity,
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
          ...materialTopicLevelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.materialTopicLevel.home.createOrEditLabel" data-cy="MaterialTopicLevelCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.materialTopicLevel.home.createOrEditLabel">
              Create or edit a MaterialTopicLevel
            </Translate>
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
                  id="material-topic-level-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.materialTopicLevel.titleUz')}
                id="material-topic-level-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.materialTopicLevel.titleRu')}
                id="material-topic-level-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.materialTopicLevel.titleKr')}
                id="material-topic-level-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/material-topic-level" replace color="info">
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

export default MaterialTopicLevelUpdate;
