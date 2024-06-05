import { type Solution } from '@domains';

export const initialSolutions: Solution[] = [
  {
    status: 'REVIEW_STAGE',
    studentId: 1,
    branch: 'http:/solutions/1',
    projectId: 57233480,
    sourceCommitId: 'main',
    approveScore: 0,
    reviewScore: 0,
    solutionAttempts: [],
  },
  {
    status: 'REVIEW_STAGE',
    studentId: 2,
    branch: 'http:/solutions/2',
    approveScore: 1,
    reviewScore: 2,
    projectId: 57233481,
    sourceCommitId: 'main',
    solutionAttempts: [
      {
        commitId: 'unknown',
        createdAt: '2024-03-04T10:29:28.265Z',
      },
      {
        commitId: 'develop',
        createdAt: '2024-03-12T10:29:28.265Z',
      },
    ],
  },
  {
    status: 'REVIEW_STAGE',
    studentId: 3,
    branch: 'http:/solutions/2',
    approveScore: 0,
    reviewScore: 2,
    projectId: 57233483,
    sourceCommitId: 'main',
    solutionAttempts: [
      {
        commitId: '1',
        createdAt: '2024-03-04T10:29:28.265Z',
      },
    ],
  },
];
