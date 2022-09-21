export interface IGreeting {
  id?: number;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
}

export const defaultValue: Readonly<IGreeting> = {};
