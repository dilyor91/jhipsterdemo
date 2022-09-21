import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities } from './address.reducer';

export const Address = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const addressList = useAppSelector(state => state.address.entities);
  const loading = useAppSelector(state => state.address.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="address-heading" data-cy="AddressHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.address.home.title">Addresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.address.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/address/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.address.home.createLabel">Create new Address</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {addressList && addressList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.titleUz">Title Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.titleRu">Title Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.titleKr">Title Kr</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.contentUz">Content Uz</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.contentRu">Content Ru</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.address.contentKr">Content Kr</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {addressList.map((address, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/address/${address.id}`} color="link" size="sm">
                      {address.id}
                    </Button>
                  </td>
                  <td>{address.titleUz}</td>
                  <td>{address.titleRu}</td>
                  <td>{address.titleKr}</td>
                  <td>{address.contentUz}</td>
                  <td>{address.contentRu}</td>
                  <td>{address.contentKr}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/address/${address.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/address/${address.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/address/${address.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.address.home.notFound">No Addresses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Address;
