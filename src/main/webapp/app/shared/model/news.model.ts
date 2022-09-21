import dayjs from 'dayjs';

export interface INews {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  postedDate?: string | null;
  status?: boolean | null;
}

export const defaultValue: Readonly<INews> = {
  status: false,
};
