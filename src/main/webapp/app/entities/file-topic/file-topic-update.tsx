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
import { IFileTopic } from 'app/shared/model/file-topic.model';
import { getEntity, updateEntity, createEntity, reset } from './file-topic.reducer';

export const FileTopicUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const materialTopicLevels = useAppSelector(state => state.materialTopicLevel.entities);
  const fileTopicEntity = useAppSelector(state => state.fileTopic.entity);
  const loading = useAppSelector(state => state.fileTopic.loading);
  const updating = useAppSelector(state => state.fileTopic.updating);
  const updateSuccess = useAppSelector(state => state.fileTopic.updateSuccess);

  const handleClose = () => {
    navigate('/file-topic');
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
      ...fileTopicEntity,
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
          ...fileTopicEntity,
          materialTopicLevel: fileTopicEntity?.materialTopicLevel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.fileTopic.home.createOrEditLabel" data-cy="FileTopicCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.fileTopic.home.createOrEditLabel">Create or edit a FileTopic</Translate>
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
                  id="file-topic-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.fileOrginalName')}
                id="file-topic-fileOrginalName"
                name="fileOrginalName"
                data-cy="fileOrginalName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.fileNameUz')}
                id="file-topic-fileNameUz"
                name="fileNameUz"
                data-cy="fileNameUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.fileNameRu')}
                id="file-topic-fileNameRu"
                name="fileNameRu"
                data-cy="fileNameRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.fileNameKr')}
                id="file-topic-fileNameKr"
                name="fileNameKr"
                data-cy="fileNameKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.fileType')}
                id="file-topic-fileType"
                name="fileType"
                data-cy="fileType"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.fileSize')}
                id="file-topic-fileSize"
                name="fileSize"
                data-cy="fileSize"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.fileTopic.filePath')}
                id="file-topic-filePath"
                name="filePath"
                data-cy="filePath"
                type="text"
              />
              <ValidatedField
                id="file-topic-materialTopicLevel"
                name="materialTopicLevel"
                data-cy="materialTopicLevel"
                label={translate('jhipsterSampleApplicationApp.fileTopic.materialTopicLevel')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/file-topic" replace color="info">
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

export default FileTopicUpdate;
