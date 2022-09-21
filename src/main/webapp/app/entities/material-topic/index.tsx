import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MaterialTopic from './material-topic';
import MaterialTopicDetail from './material-topic-detail';
import MaterialTopicUpdate from './material-topic-update';
import MaterialTopicDeleteDialog from './material-topic-delete-dialog';

const MaterialTopicRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MaterialTopic />} />
    <Route path="new" element={<MaterialTopicUpdate />} />
    <Route path=":id">
      <Route index element={<MaterialTopicDetail />} />
      <Route path="edit" element={<MaterialTopicUpdate />} />
      <Route path="delete" element={<MaterialTopicDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaterialTopicRoutes;
