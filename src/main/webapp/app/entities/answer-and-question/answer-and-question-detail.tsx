import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './answer-and-question.reducer';

export const AnswerAndQuestionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const answerAndQuestionEntity = useAppSelector(state => state.answerAndQuestion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="answerAndQuestionDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.detail.title">AnswerAndQuestion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.id}</dd>
          <dt>
            <span id="questionUz">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.questionUz">Question Uz</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.questionUz}</dd>
          <dt>
            <span id="questionRu">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.questionRu">Question Ru</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.questionRu}</dd>
          <dt>
            <span id="questionKr">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.questionKr">Question Kr</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.questionKr}</dd>
          <dt>
            <span id="answerUz">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.answerUz">Answer Uz</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.answerUz}</dd>
          <dt>
            <span id="answerRu">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.answerRu">Answer Ru</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.answerRu}</dd>
          <dt>
            <span id="answerKr">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.answerKr">Answer Kr</Translate>
            </span>
          </dt>
          <dd>{answerAndQuestionEntity.answerKr}</dd>
        </dl>
        <Button tag={Link} to="/answer-and-question" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer-and-question/${answerAndQuestionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnswerAndQuestionDetail;
