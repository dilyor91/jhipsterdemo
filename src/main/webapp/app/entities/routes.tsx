import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Banner from './banner';
import Logo from './logo';
import Greeting from './greeting';
import CenterStructure from './center-structure';
import OurHistory from './our-history';
import File from './file';
import WorkPlan from './work-plan';
import Address from './address';
import News from './news';
import Events from './events';
import StudyAtKorea from './study-at-korea';
import TimeTable from './time-table';
import Institution from './institution';
import AnswerAndQuestion from './answer-and-question';
import Image from './image';
import Album from './album';
import Region from './region';
import Country from './country';
import Location from './location';
import Department from './department';
import Task from './task';
import Employee from './employee';
import Job from './job';
import JobHistory from './job-history';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="banner/*" element={<Banner />} />
        <Route path="logo/*" element={<Logo />} />
        <Route path="greeting/*" element={<Greeting />} />
        <Route path="center-structure/*" element={<CenterStructure />} />
        <Route path="our-history/*" element={<OurHistory />} />
        <Route path="file/*" element={<File />} />
        <Route path="work-plan/*" element={<WorkPlan />} />
        <Route path="address/*" element={<Address />} />
        <Route path="news/*" element={<News />} />
        <Route path="events/*" element={<Events />} />
        <Route path="study-at-korea/*" element={<StudyAtKorea />} />
        <Route path="time-table/*" element={<TimeTable />} />
        <Route path="institution/*" element={<Institution />} />
        <Route path="answer-and-question/*" element={<AnswerAndQuestion />} />
        <Route path="image/*" element={<Image />} />
        <Route path="album/*" element={<Album />} />
        <Route path="region/*" element={<Region />} />
        <Route path="country/*" element={<Country />} />
        <Route path="location/*" element={<Location />} />
        <Route path="department/*" element={<Department />} />
        <Route path="task/*" element={<Task />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="job/*" element={<Job />} />
        <Route path="job-history/*" element={<JobHistory />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
