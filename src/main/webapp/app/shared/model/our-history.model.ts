import dayjs from 'dayjs';

export interface IOurHistory {
  id?: number;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  postedDate?: string | null;
}

export const defaultValue: Readonly<IOurHistory> = {};
