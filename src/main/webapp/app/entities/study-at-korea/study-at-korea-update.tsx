import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudyAtKorea } from 'app/shared/model/study-at-korea.model';
import { getEntity, updateEntity, createEntity, reset } from './study-at-korea.reducer';

export const StudyAtKoreaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const studyAtKoreaEntity = useAppSelector(state => state.studyAtKorea.entity);
  const loading = useAppSelector(state => state.studyAtKorea.loading);
  const updating = useAppSelector(state => state.studyAtKorea.updating);
  const updateSuccess = useAppSelector(state => state.studyAtKorea.updateSuccess);

  const handleClose = () => {
    navigate('/study-at-korea');
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
      ...studyAtKoreaEntity,
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
          ...studyAtKoreaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.studyAtKorea.home.createOrEditLabel" data-cy="StudyAtKoreaCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.studyAtKorea.home.createOrEditLabel">
              Create or edit a StudyAtKorea
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
                  id="study-at-korea-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.studyAtKorea.titleUz')}
                id="study-at-korea-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.studyAtKorea.titleRu')}
                id="study-at-korea-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.studyAtKorea.titleKr')}
                id="study-at-korea-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.studyAtKorea.contentUz')}
                id="study-at-korea-contentUz"
                name="contentUz"
                data-cy="contentUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.studyAtKorea.contentRu')}
                id="study-at-korea-contentRu"
                name="contentRu"
                data-cy="contentRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.studyAtKorea.contentKr')}
                id="study-at-korea-contentKr"
                name="contentKr"
                data-cy="contentKr"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/study-at-korea" replace color="info">
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

export default StudyAtKoreaUpdate;
