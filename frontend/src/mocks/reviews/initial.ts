import { type Review } from '@domains';

export const initialReviews: Review[] = [
  {
    reviewId: 1,
    status: 'REVIEWER_FOUND',
    projectId: 57233481,
    commitId: 'develop',
    sourceCommitId: 'main',
  },
  {
    status: 'CORRECTIONS_REQUIRED',
    reviewId: 2,
    projectId: 57233481,
    commitId: 'develop',
    sourceCommitId: 'main',
    reviewAttempts: [
      {
        solutionAttemptId: 1,
        finishedAt: '2024-03-04T10:29:28.265Z',
        resolution: 'test',
        reviewAttemptId: 1,
        reviewId: 2,
        createdAt: '2024-03-04T10:29:28.265Z',
      },
      {
        solutionAttemptId: 2,
        finishedAt: '2024-03-04T10:29:28.265Z',
        resolution: 'test',
        reviewAttemptId: 2,
        reviewId: 2,
        createdAt: '2024-03-04T10:29:28.265Z',
      },
    ],
  },
];
