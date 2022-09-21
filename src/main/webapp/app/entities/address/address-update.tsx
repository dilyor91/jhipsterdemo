import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAddress } from 'app/shared/model/address.model';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';

export const AddressUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updating = useAppSelector(state => state.address.updating);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);

  const handleClose = () => {
    navigate('/address');
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
      ...addressEntity,
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
          ...addressEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.address.home.createOrEditLabel" data-cy="AddressCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.address.home.createOrEditLabel">Create or edit a Address</Translate>
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
                  id="address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.address.titleUz')}
                id="address-titleUz"
                name="titleUz"
                data-cy="titleUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.address.titleRu')}
                id="address-titleRu"
                name="titleRu"
                data-cy="titleRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.address.titleKr')}
                id="address-titleKr"
                name="titleKr"
                data-cy="titleKr"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.address.contentUz')}
                id="address-contentUz"
                name="contentUz"
                data-cy="contentUz"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.address.contentRu')}
                id="address-contentRu"
                name="contentRu"
                data-cy="contentRu"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.address.contentKr')}
                id="address-contentKr"
                name="contentKr"
                data-cy="contentKr"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/address" replace color="info">
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

export default AddressUpdate;
