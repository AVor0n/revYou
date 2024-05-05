import { type Solution } from '@domains';

export interface SolutionSchema {
  solutionInfo: Solution | null;
  solutions: Solution[] | null;
  error: string;
}
