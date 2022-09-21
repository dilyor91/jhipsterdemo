import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StudyAtKorea from './study-at-korea';
import StudyAtKoreaDetail from './study-at-korea-detail';
import StudyAtKoreaUpdate from './study-at-korea-update';
import StudyAtKoreaDeleteDialog from './study-at-korea-delete-dialog';

const StudyAtKoreaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StudyAtKorea />} />
    <Route path="new" element={<StudyAtKoreaUpdate />} />
    <Route path=":id">
      <Route index element={<StudyAtKoreaDetail />} />
      <Route path="edit" element={<StudyAtKoreaUpdate />} />
      <Route path="delete" element={<StudyAtKoreaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StudyAtKoreaRoutes;
