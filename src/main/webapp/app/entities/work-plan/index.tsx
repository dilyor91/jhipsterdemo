import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WorkPlan from './work-plan';
import WorkPlanDetail from './work-plan-detail';
import WorkPlanUpdate from './work-plan-update';
import WorkPlanDeleteDialog from './work-plan-delete-dialog';

const WorkPlanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WorkPlan />} />
    <Route path="new" element={<WorkPlanUpdate />} />
    <Route path=":id">
      <Route index element={<WorkPlanDetail />} />
      <Route path="edit" element={<WorkPlanUpdate />} />
      <Route path="delete" element={<WorkPlanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkPlanRoutes;
