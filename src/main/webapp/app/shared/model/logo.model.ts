export interface ILogo {
  id?: number;
  name?: string | null;
  logoData?: string | null;
  status?: boolean | null;
}

export const defaultValue: Readonly<ILogo> = {
  status: false,
};
