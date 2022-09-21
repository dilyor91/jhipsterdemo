import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInstitution } from 'app/shared/model/institution.model';
import { getEntities } from './institution.reducer';

export const Institution = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const institutionList = useAppSelector(state => state.institution.entities);
  const loading = useAppSelector(state => state.institution.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="institution-heading" data-cy="InstitutionHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.institution.home.title">Institutions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.institution.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/institution/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.institution.home.createLabel">Create new Institution</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {institutionList && institutionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.institutionType">Institution Type</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.contentUz">Content Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.contentRu">Content Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.contentKr">Content Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.logoName">Logo Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.institution.logoData">Logo Data</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {institutionList.map((institution, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/institution/${institution.id}`} color="link" size="sm">
                      {institution.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`jhipsterSampleApplicationApp.InstitutType.${institution.institutionType}`} />
                  </td>
                  <td>{institution.titleUz}</td>
                  <td>{institution.titleRu}</td>
                  <td>{institution.titleKr}</td>
                  <td>{institution.contentUz}</td>
                  <td>{institution.contentRu}</td>
                  <td>{institution.contentKr}</td>
                  <td>{institution.logoName}</td>
                  <td>{institution.logoData}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/institution/${institution.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/institution/${institution.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/institution/${institution.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.institution.home.notFound">No Institutions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Institution;
