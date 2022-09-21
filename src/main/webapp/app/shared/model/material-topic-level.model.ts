import { IFileTopic } from 'app/shared/model/file-topic.model';

export interface IMaterialTopicLevel {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  fileTopics?: IFileTopic[] | null;
}

export const defaultValue: Readonly<IMaterialTopicLevel> = {};
