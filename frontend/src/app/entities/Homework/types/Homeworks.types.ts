import { type GetHomework } from '@domains/__generated__';

export interface HomeworkSchema {
  homeworks: GetHomework[] | null;
  error: string;
}
