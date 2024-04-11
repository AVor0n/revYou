import { type Homework } from '@domains';

export interface HomeworkSchema {
  homeworkForEdit: Partial<Homework> | null;
  homeworks: Homework[] | null;
  error: string;
}
