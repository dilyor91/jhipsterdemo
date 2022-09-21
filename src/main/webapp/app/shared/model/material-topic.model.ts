import { IMaterialTopicLevel } from 'app/shared/model/material-topic-level.model';

export interface IMaterialTopic {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  materialTopicLevel?: IMaterialTopicLevel | null;
}

export const defaultValue: Readonly<IMaterialTopic> = {};
