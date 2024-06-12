import { type Review, type ReviewInfo, type UserDetailDto } from '@api';

export interface FullReviewInfo extends ReviewInfo {
  sourceCommitId: string;
}

export interface SolutionSchema {
  mySolutions: Review[] | null;
  allSolutions: FullReviewInfo[] | null;
  solutionsForReview: Review[] | null;
  requestInProgress: Record<string, boolean>;
  availableReviewers: UserDetailDto[] | null;
  error: string;
}
