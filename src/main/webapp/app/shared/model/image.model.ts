import { IAlbum } from 'app/shared/model/album.model';

export interface IImage {
  id?: number;
  orginalName?: string | null;
  name?: string | null;
  imageData?: string | null;
  mainlyPhoto?: boolean | null;
  image?: IAlbum | null;
}

export const defaultValue: Readonly<IImage> = {
  mainlyPhoto: false,
};
