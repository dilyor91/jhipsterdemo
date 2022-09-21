import { PlanType } from 'app/shared/model/enumerations/plan-type.model';

export interface IWorkPlan {
  id?: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  planType?: PlanType | null;
}

export const defaultValue: Readonly<IWorkPlan> = {};
