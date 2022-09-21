import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnswerAndQuestion } from 'app/shared/model/answer-and-question.model';
import { getEntity, updateEntity, createEntity, reset } from './answer-and-question.reducer';

export const AnswerAndQuestionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const answerAndQuestionEntity = useAppSelector(state => state.answerAndQuestion.entity);
  const loading = useAppSelector(state => state.answerAndQuestion.loading);
  const updating = useAppSelector(state => state.answerAndQuestion.updating);
  const updateSuccess = useAppSelector(state => state.answerAndQuestion.updateSuccess);

  const handleClose = () => {
    navigate('/answer-and-question');
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
      ...answerAndQuestionEntity,
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
          ...answerAndQuestionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.answerAndQuestion.home.createOrEditLabel" data-cy="AnswerAndQuestionCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.home.createOrEditLabel">
              Create or edit a AnswerAndQuestion
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
                  id="answer-and-question-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.answerAndQuestion.questionUz')}
                id="answer-and-question-questionUz"
                name="questionUz"
                data-cy="questionUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.answerAndQuestion.questionRu')}
                id="answer-and-question-questionRu"
                name="questionRu"
                data-cy="questionRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.answerAndQuestion.questionKr')}
                id="answer-and-question-questionKr"
                name="questionKr"
                data-cy="questionKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.answerAndQuestion.answerUz')}
                id="answer-and-question-answerUz"
                name="answerUz"
                data-cy="answerUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.answerAndQuestion.answerRu')}
                id="answer-and-question-answerRu"
                name="answerRu"
                data-cy="answerRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.answerAndQuestion.answerKr')}
                id="answer-and-question-answerKr"
                name="answerKr"
                data-cy="answerKr"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/answer-and-question" replace color="info">
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

export default AnswerAndQuestionUpdate;
