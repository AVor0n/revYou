import { type SolutionInfo } from 'app';

export interface MockSolution extends SolutionInfo {
  id: number;
}

export const initialSolutions: MockSolution[] = [
  {
    id: 1,
    authorId: '1',
    projectId: '57233481',
    sourceCommitHash: 'main',
    targetCommitHash: 'develop',
  },
];
