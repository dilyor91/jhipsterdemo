import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MaterialTopicLevel from './material-topic-level';
import MaterialTopicLevelDetail from './material-topic-level-detail';
import MaterialTopicLevelUpdate from './material-topic-level-update';
import MaterialTopicLevelDeleteDialog from './material-topic-level-delete-dialog';

const MaterialTopicLevelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MaterialTopicLevel />} />
    <Route path="new" element={<MaterialTopicLevelUpdate />} />
    <Route path=":id">
      <Route index element={<MaterialTopicLevelDetail />} />
      <Route path="edit" element={<MaterialTopicLevelUpdate />} />
      <Route path="delete" element={<MaterialTopicLevelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaterialTopicLevelRoutes;
