import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBanner } from 'app/shared/model/banner.model';
import { getEntities } from './banner.reducer';

export const Banner = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const bannerList = useAppSelector(state => state.banner.entities);
  const loading = useAppSelector(state => state.banner.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="banner-heading" data-cy="BannerHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.banner.home.title">Banners</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.banner.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/banner/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.banner.home.createLabel">Create new Banner</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bannerList && bannerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.banner.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.banner.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.banner.bannerData">Banner Data</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.banner.status">Status</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bannerList.map((banner, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/banner/${banner.id}`} color="link" size="sm">
                      {banner.id}
                    </Button>
                  </td>
                  <td>{banner.name}</td>
                  <td>{banner.bannerData}</td>
                  <td>{banner.status ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/banner/${banner.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/banner/${banner.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/banner/${banner.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterSampleApplicationApp.banner.home.notFound">No Banners found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Banner;
