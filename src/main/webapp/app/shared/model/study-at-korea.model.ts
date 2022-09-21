import { IFile } from 'app/shared/model/file.model';

export interface IStudyAtKorea {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  files?: IFile[] | null;
}

export const defaultValue: Readonly<IStudyAtKorea> = {};
