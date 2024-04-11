import { type Homework } from '@domains';

export interface HomeworkSchema {
  selectedHomework: Homework | null;
  homeworkForEdit: Partial<Homework> | null;
  homeworks: Homework[] | null;
  error: string;
}
