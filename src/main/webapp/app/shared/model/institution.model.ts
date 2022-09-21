import { IFile } from 'app/shared/model/file.model';
import { InstitutType } from 'app/shared/model/enumerations/institut-type.model';

export interface IInstitution {
  id?: number;
  institutionType?: InstitutType | null;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  logoName?: string | null;
  logoData?: string | null;
  files?: IFile[] | null;
}

export const defaultValue: Readonly<IInstitution> = {};
