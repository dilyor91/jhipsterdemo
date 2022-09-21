import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FileTopic from './file-topic';
import FileTopicDetail from './file-topic-detail';
import FileTopicUpdate from './file-topic-update';
import FileTopicDeleteDialog from './file-topic-delete-dialog';

const FileTopicRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FileTopic />} />
    <Route path="new" element={<FileTopicUpdate />} />
    <Route path=":id">
      <Route index element={<FileTopicDetail />} />
      <Route path="edit" element={<FileTopicUpdate />} />
      <Route path="delete" element={<FileTopicDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FileTopicRoutes;
