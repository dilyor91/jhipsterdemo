import dayjs from 'dayjs';

export interface ITimeTable {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  postedDate?: string | null;
}

export const defaultValue: Readonly<ITimeTable> = {};
