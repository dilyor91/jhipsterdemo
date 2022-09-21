export interface IBanner {
  id?: number;
  name?: string | null;
  bannerData?: string | null;
  status?: boolean | null;
}

export const defaultValue: Readonly<IBanner> = {
  status: false,
};
