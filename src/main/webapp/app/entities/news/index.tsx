import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import News from './news';
import NewsDetail from './news-detail';
import NewsUpdate from './news-update';
import NewsDeleteDialog from './news-delete-dialog';

const NewsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<News />} />
    <Route path="new" element={<NewsUpdate />} />
    <Route path=":id">
      <Route index element={<NewsDetail />} />
      <Route path="edit" element={<NewsUpdate />} />
      <Route path="delete" element={<NewsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NewsRoutes;
