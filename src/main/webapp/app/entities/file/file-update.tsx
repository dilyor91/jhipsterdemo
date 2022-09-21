import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInstitution } from 'app/shared/model/institution.model';
import { getEntities as getInstitutions } from 'app/entities/institution/institution.reducer';
import { IStudyAtKorea } from 'app/shared/model/study-at-korea.model';
import { getEntities as getStudyAtKoreas } from 'app/entities/study-at-korea/study-at-korea.reducer';
import { IFile } from 'app/shared/model/file.model';
import { FileEntity } from 'app/shared/model/enumerations/file-entity.model';
import { getEntity, updateEntity, createEntity, reset } from './file.reducer';

export const FileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const institutions = useAppSelector(state => state.institution.entities);
  const studyAtKoreas = useAppSelector(state => state.studyAtKorea.entities);
  const fileEntity = useAppSelector(state => state.file.entity);
  const loading = useAppSelector(state => state.file.loading);
  const updating = useAppSelector(state => state.file.updating);
  const updateSuccess = useAppSelector(state => state.file.updateSuccess);
  const fileEntityValues = Object.keys(FileEntity);

  const handleClose = () => {
    navigate('/file');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInstitutions({}));
    dispatch(getStudyAtKoreas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fileEntity,
      ...values,
      institution: institutions.find(it => it.id.toString() === values.institution.toString()),
      studyAtKorea: studyAtKoreas.find(it => it.id.toString() === values.studyAtKorea.toString()),
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
          fileEntity: 'STUDYATKOREA',
          ...fileEntity,
          institution: fileEntity?.institution?.id,
          studyAtKorea: fileEntity?.studyAtKorea?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.file.home.createOrEditLabel" data-cy="FileCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.file.home.createOrEditLabel">Create or edit a File</Translate>
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
                  id="file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.file.orginalName')}
                id="file-orginalName"
                name="orginalName"
                data-cy="orginalName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.file.fileName')}
                id="file-fileName"
                name="fileName"
                data-cy="fileName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.file.fileSize')}
                id="file-fileSize"
                name="fileSize"
                data-cy="fileSize"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.file.fileFormat')}
                id="file-fileFormat"
                name="fileFormat"
                data-cy="fileFormat"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.file.filePath')}
                id="file-filePath"
                name="filePath"
                data-cy="filePath"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.file.fileEntity')}
                id="file-fileEntity"
                name="fileEntity"
                data-cy="fileEntity"
                type="select"
              >
                {fileEntityValues.map(fileEntity => (
                  <option value={fileEntity} key={fileEntity}>
                    {translate('jhipsterSampleApplicationApp.FileEntity.' + fileEntity)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="file-institution"
                name="institution"
                data-cy="institution"
                label={translate('jhipsterSampleApplicationApp.file.institution')}
                type="select"
              >
                <option value="" key="0" />
                {institutions
                  ? institutions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="file-studyAtKorea"
                name="studyAtKorea"
                data-cy="studyAtKorea"
                label={translate('jhipsterSampleApplicationApp.file.studyAtKorea')}
                type="select"
              >
                <option value="" key="0" />
                {studyAtKoreas
                  ? studyAtKoreas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/file" replace color="info">
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

export default FileUpdate;
