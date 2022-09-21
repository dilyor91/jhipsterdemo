import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CenterStructure from './center-structure';
import CenterStructureDetail from './center-structure-detail';
import CenterStructureUpdate from './center-structure-update';
import CenterStructureDeleteDialog from './center-structure-delete-dialog';

const CenterStructureRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CenterStructure />} />
    <Route path="new" element={<CenterStructureUpdate />} />
    <Route path=":id">
      <Route index element={<CenterStructureDetail />} />
      <Route path="edit" element={<CenterStructureUpdate />} />
      <Route path="delete" element={<CenterStructureDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CenterStructureRoutes;
