import { IImage } from 'app/shared/model/image.model';

export interface IAlbum {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  images?: IImage[] | null;
}

export const defaultValue: Readonly<IAlbum> = {};
