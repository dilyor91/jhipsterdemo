import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILogo } from 'app/shared/model/logo.model';
import { getEntities } from './logo.reducer';

export const Logo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const logoList = useAppSelector(state => state.logo.entities);
  const loading = useAppSelector(state => state.logo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="logo-heading" data-cy="LogoHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.logo.home.title">Logos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.logo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/logo/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.logo.home.createLabel">Create new Logo</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {logoList && logoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.logo.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.logo.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.logo.logoData">Logo Data</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.logo.status">Status</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {logoList.map((logo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/logo/${logo.id}`} color="link" size="sm">
                      {logo.id}
                    </Button>
                  </td>
                  <td>{logo.name}</td>
                  <td>{logo.logoData}</td>
                  <td>{logo.status ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/logo/${logo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/logo/${logo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/logo/${logo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.logo.home.notFound">No Logos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Logo;
