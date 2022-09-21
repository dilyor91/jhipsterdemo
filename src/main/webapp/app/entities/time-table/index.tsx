import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TimeTable from './time-table';
import TimeTableDetail from './time-table-detail';
import TimeTableUpdate from './time-table-update';
import TimeTableDeleteDialog from './time-table-delete-dialog';

const TimeTableRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TimeTable />} />
    <Route path="new" element={<TimeTableUpdate />} />
    <Route path=":id">
      <Route index element={<TimeTableDetail />} />
      <Route path="edit" element={<TimeTableUpdate />} />
      <Route path="delete" element={<TimeTableDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TimeTableRoutes;
