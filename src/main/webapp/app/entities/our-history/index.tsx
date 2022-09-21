import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OurHistory from './our-history';
import OurHistoryDetail from './our-history-detail';
import OurHistoryUpdate from './our-history-update';
import OurHistoryDeleteDialog from './our-history-delete-dialog';

const OurHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OurHistory />} />
    <Route path="new" element={<OurHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<OurHistoryDetail />} />
      <Route path="edit" element={<OurHistoryUpdate />} />
      <Route path="delete" element={<OurHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OurHistoryRoutes;
