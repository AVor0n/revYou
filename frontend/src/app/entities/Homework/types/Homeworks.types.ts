import { type GetHomework } from '@domains/__generated__';

export interface HomeworkSchema {
  homeworkForEdit: GetHomework | null;
  homeworks: GetHomework[] | null;
  error: string;
}
