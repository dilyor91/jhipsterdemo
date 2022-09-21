import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMaterialTopicLevel } from 'app/shared/model/material-topic-level.model';
import { getEntities as getMaterialTopicLevels } from 'app/entities/material-topic-level/material-topic-level.reducer';
import { IMaterialTopic } from 'app/shared/model/material-topic.model';
import { getEntity, updateEntity, createEntity, reset } from './material-topic.reducer';

export const MaterialTopicUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const materialTopicLevels = useAppSelector(state => state.materialTopicLevel.entities);
  const materialTopicEntity = useAppSelector(state => state.materialTopic.entity);
  const loading = useAppSelector(state => state.materialTopic.loading);
  const updating = useAppSelector(state => state.materialTopic.updating);
  const updateSuccess = useAppSelector(state => state.materialTopic.updateSuccess);

  const handleClose = () => {
    navigate('/material-topic');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMaterialTopicLevels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...materialTopicEntity,
      ...values,
      materialTopicLevel: materialTopicLevels.find(it => it.id.toString() === values.materialTopicLevel.toString()),
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
          ...materialTopicEntity,
          materialTopicLevel: materialTopicEntity?.materialTopicLevel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.materialTopic.home.createOrEditLabel" data-cy="MaterialTopicCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.materialTopic.home.createOrEditLabel">
              Create or edit a MaterialTopic
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
                  id="material-topic-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.materialTopic.titleUz')}
                id="material-topic-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.materialTopic.titleRu')}
                id="material-topic-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.materialTopic.titleKr')}
                id="material-topic-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <ValidatedField
                id="material-topic-materialTopicLevel"
                name="materialTopicLevel"
                data-cy="materialTopicLevel"
                label={translate('jhipsterSampleApplicationApp.materialTopic.materialTopicLevel')}
                type="select"
              >
                <option value="" key="0" />
                {materialTopicLevels
                  ? materialTopicLevels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/material-topic" replace color="info">
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

export default MaterialTopicUpdate;
