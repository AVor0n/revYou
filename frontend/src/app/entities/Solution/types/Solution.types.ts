import { type Review } from '@domains';

export interface SolutionSchema {
  mySolutions: Review[] | null;
  solutionsForReview: Review[] | null;
  error: string;
}
