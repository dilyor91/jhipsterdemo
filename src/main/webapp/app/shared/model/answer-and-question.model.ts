export interface IAnswerAndQuestion {
  id?: number;
  questionUz?: string | null;
  questionRu?: string | null;
  questionKr?: string | null;
  answerUz?: string | null;
  answerRu?: string | null;
  answerKr?: string | null;
}

export const defaultValue: Readonly<IAnswerAndQuestion> = {};
