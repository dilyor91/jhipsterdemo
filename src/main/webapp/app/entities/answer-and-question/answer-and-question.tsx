import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnswerAndQuestion } from 'app/shared/model/answer-and-question.model';
import { getEntities } from './answer-and-question.reducer';

export const AnswerAndQuestion = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const answerAndQuestionList = useAppSelector(state => state.answerAndQuestion.entities);
  const loading = useAppSelector(state => state.answerAndQuestion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="answer-and-question-heading" data-cy="AnswerAndQuestionHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.home.title">Answer And Questions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/answer-and-question/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.home.createLabel">
              Create new Answer And Question
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {answerAndQuestionList && answerAndQuestionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.questionUz">Question Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.questionRu">Question Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.questionKr">Question Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.answerUz">Answer Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.answerRu">Answer Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.answerKr">Answer Kr</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {answerAndQuestionList.map((answerAndQuestion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/answer-and-question/${answerAndQuestion.id}`} color="link" size="sm">
                      {answerAndQuestion.id}
                    </Button>
                  </td>
                  <td>{answerAndQuestion.questionUz}</td>
                  <td>{answerAndQuestion.questionRu}</td>
                  <td>{answerAndQuestion.questionKr}</td>
                  <td>{answerAndQuestion.answerUz}</td>
                  <td>{answerAndQuestion.answerRu}</td>
                  <td>{answerAndQuestion.answerKr}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/answer-and-question/${answerAndQuestion.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/answer-and-question/${answerAndQuestion.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/answer-and-question/${answerAndQuestion.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.answerAndQuestion.home.notFound">No Answer And Questions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AnswerAndQuestion;
