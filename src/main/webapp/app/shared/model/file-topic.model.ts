import { IMaterialTopicLevel } from 'app/shared/model/material-topic-level.model';

export interface IFileTopic {
  id?: number;
  fileOrginalName?: string | null;
  fileNameUz?: string | null;
  fileNameRu?: string | null;
  fileNameKr?: string | null;
  fileType?: string | null;
  fileSize?: number | null;
  filePath?: string | null;
  materialTopicLevel?: IMaterialTopicLevel | null;
}

export const defaultValue: Readonly<IFileTopic> = {};
