import { type CommentsThread, type Review } from '@domains';

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

export const initialThreads: CommentsThread[] = [
  {
    threadId: 4,
    authorId: 2,
    commitSha: 'develop',
    filePath: '/tsconfig.json',
    status: 'ACTIVE',
    comments: [
      {
        commentId: 7,
        authorId: 2,
        content:
          'lorem  ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua ut enim ad minim veniam quis.',
        createdAt: '2024-03-04T10:29:28.265Z',
        updatedAt: '2024-03-04T10:29:28.265Z',
      },
    ],
    startLine: 15,
    startSymbol: 5,
    endLine: 20,
    endSymbol: 32,
  },
  {
    threadId: 2,
    authorId: 1,
    commitSha: 'develop',
    filePath: '/tsconfig.json',
    status: 'ACTIVE',
    comments: [
      {
        commentId: 1,
        authorId: 1,
        content: 'Тестовый комментарий',
        createdAt: '2024-05-29T09:34:21.958Z',
        updatedAt: '2024-03-04T10:29:28.265Z',
      },
      {
        commentId: 2,
        authorId: 2,
        content: 'Тестовый комментарий № 2',
        createdAt: '2024-03-04T10:30:28.265Z',
        updatedAt: '2024-03-04T10:30:28.265Z',
      },
    ],
    startLine: 5,
    startSymbol: 2,
    endLine: 10,
    endSymbol: 5,
  },
  {
    threadId: 3,
    authorId: 2,
    commitSha: 'develop',
    filePath: '/src/app/global.css',
    status: 'ACTIVE',
    comments: [
      {
        commentId: 5,
        authorId: 2,
        content: 'Тестовый комментарий',
        createdAt: '2024-03-04T10:29:28.265Z',
        updatedAt: '2024-03-04T10:29:28.265Z',
      },
      {
        commentId: 6,
        authorId: 1,
        content: 'Тестовый комментарий № 2',
        createdAt: '2024-03-04T10:30:28.265Z',
        updatedAt: '2024-03-04T10:30:28.265Z',
      },
    ],

    startLine: 1,
    startSymbol: 2,
    endLine: 3,
    endSymbol: 5,
  },
  {
    threadId: 1,
    authorId: 1,
    commitSha: 'develop',
    filePath: '/package.json',
    status: 'ACTIVE',
    comments: [
      {
        commentId: 3,
        authorId: 1,
        content: 'Тестовый комментарий',
        createdAt: '2024-03-04T10:29:28.265Z',
        updatedAt: '2024-03-04T10:29:28.265Z',
      },
      {
        commentId: 4,
        authorId: 1,
        content: 'Тестовый комментарий № 2',
        createdAt: '2024-03-04T10:30:28.265Z',
        updatedAt: '2024-03-04T10:30:28.265Z',
      },
    ],

    startLine: 1,
    startSymbol: 2,
    endLine: 3,
    endSymbol: 4,
  },
];
