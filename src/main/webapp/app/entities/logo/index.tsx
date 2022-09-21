import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Logo from './logo';
import LogoDetail from './logo-detail';
import LogoUpdate from './logo-update';
import LogoDeleteDialog from './logo-delete-dialog';

const LogoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Logo />} />
    <Route path="new" element={<LogoUpdate />} />
    <Route path=":id">
      <Route index element={<LogoDetail />} />
      <Route path="edit" element={<LogoUpdate />} />
      <Route path="delete" element={<LogoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LogoRoutes;
