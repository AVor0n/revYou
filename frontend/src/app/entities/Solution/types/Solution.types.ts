import { type Solution } from '@domains';

export interface SolutionSchema {
  solutions: Solution[] | null;
  error: string;
}
