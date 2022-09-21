import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AnswerAndQuestion from './answer-and-question';
import AnswerAndQuestionDetail from './answer-and-question-detail';
import AnswerAndQuestionUpdate from './answer-and-question-update';
import AnswerAndQuestionDeleteDialog from './answer-and-question-delete-dialog';

const AnswerAndQuestionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AnswerAndQuestion />} />
    <Route path="new" element={<AnswerAndQuestionUpdate />} />
    <Route path=":id">
      <Route index element={<AnswerAndQuestionDetail />} />
      <Route path="edit" element={<AnswerAndQuestionUpdate />} />
      <Route path="delete" element={<AnswerAndQuestionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnswerAndQuestionRoutes;
