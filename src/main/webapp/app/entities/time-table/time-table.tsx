import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITimeTable } from 'app/shared/model/time-table.model';
import { getEntities } from './time-table.reducer';

export const TimeTable = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const timeTableList = useAppSelector(state => state.timeTable.entities);
  const loading = useAppSelector(state => state.timeTable.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="time-table-heading" data-cy="TimeTableHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.timeTable.home.title">Time Tables</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.timeTable.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/time-table/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.timeTable.home.createLabel">Create new Time Table</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {timeTableList && timeTableList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.contentUz">Content Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.contentRu">Content Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.contentKr">Content Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.timeTable.postedDate">Posted Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {timeTableList.map((timeTable, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/time-table/${timeTable.id}`} color="link" size="sm">
                      {timeTable.id}
                    </Button>
                  </td>
                  <td>{timeTable.titleUz}</td>
                  <td>{timeTable.titleRu}</td>
                  <td>{timeTable.titleKr}</td>
                  <td>{timeTable.contentUz}</td>
                  <td>{timeTable.contentRu}</td>
                  <td>{timeTable.contentKr}</td>
                  <td>
                    {timeTable.postedDate ? <TextFormat type="date" value={timeTable.postedDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/time-table/${timeTable.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/time-table/${timeTable.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/time-table/${timeTable.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.timeTable.home.notFound">No Time Tables found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TimeTable;
