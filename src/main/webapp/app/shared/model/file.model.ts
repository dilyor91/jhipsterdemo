import { IInstitution } from 'app/shared/model/institution.model';
import { IStudyAtKorea } from 'app/shared/model/study-at-korea.model';
import { FileEntity } from 'app/shared/model/enumerations/file-entity.model';

export interface IFile {
  id?: number;
  orginalName?: string | null;
  fileName?: string | null;
  fileSize?: number | null;
  fileFormat?: string | null;
  filePath?: string | null;
  fileEntity?: FileEntity | null;
  institution?: IInstitution | null;
  studyAtKorea?: IStudyAtKorea | null;
}

export const defaultValue: Readonly<IFile> = {};
