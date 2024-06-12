import { type Homework } from '@api';

export interface HomeworkSchema {
  selectedHomework: Homework | null;
  homeworkForEdit: Partial<Homework> | null;
  homeworks: Homework[] | null;
  error: string;
}
