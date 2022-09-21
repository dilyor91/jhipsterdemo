import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/banner">
        <Translate contentKey="global.menu.entities.banner" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/logo">
        <Translate contentKey="global.menu.entities.logo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/greeting">
        <Translate contentKey="global.menu.entities.greeting" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/center-structure">
        <Translate contentKey="global.menu.entities.centerStructure" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/our-history">
        <Translate contentKey="global.menu.entities.ourHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/file">
        <Translate contentKey="global.menu.entities.file" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/work-plan">
        <Translate contentKey="global.menu.entities.workPlan" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/news">
        <Translate contentKey="global.menu.entities.news" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/events">
        <Translate contentKey="global.menu.entities.events" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/study-at-korea">
        <Translate contentKey="global.menu.entities.studyAtKorea" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/time-table">
        <Translate contentKey="global.menu.entities.timeTable" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/institution">
        <Translate contentKey="global.menu.entities.institution" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/answer-and-question">
        <Translate contentKey="global.menu.entities.answerAndQuestion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/image">
        <Translate contentKey="global.menu.entities.image" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/album">
        <Translate contentKey="global.menu.entities.album" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/region">
        <Translate contentKey="global.menu.entities.region" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/country">
        <Translate contentKey="global.menu.entities.country" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/department">
        <Translate contentKey="global.menu.entities.department" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/task">
        <Translate contentKey="global.menu.entities.task" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job">
        <Translate contentKey="global.menu.entities.job" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-history">
        <Translate contentKey="global.menu.entities.jobHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/material-topic">
        <Translate contentKey="global.menu.entities.materialTopic" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/file-topic">
        <Translate contentKey="global.menu.entities.fileTopic" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/material-topic-level">
        <Translate contentKey="global.menu.entities.materialTopicLevel" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
