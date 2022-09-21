import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Greeting from './greeting';
import GreetingDetail from './greeting-detail';
import GreetingUpdate from './greeting-update';
import GreetingDeleteDialog from './greeting-delete-dialog';

const GreetingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Greeting />} />
    <Route path="new" element={<GreetingUpdate />} />
    <Route path=":id">
      <Route index element={<GreetingDetail />} />
      <Route path="edit" element={<GreetingUpdate />} />
      <Route path="delete" element={<GreetingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GreetingRoutes;
