import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import { type FilesTree, type FileTreeDiff, type SolutionInfo } from '../types';
import { buildFileTree } from './buildFileTree';

const getSolutionInfo = (solutionId: number) =>
  fetch(`/api/solutions/${solutionId}`).then<SolutionInfo>(res => res.json());

const getFileTreeDiff = (projectId: string, from: string, to: string) =>
  fetch(`https://gitlab.com/api/v4/projects/${projectId}/repository/compare?from=${from}&to=${to}`).then<FileTreeDiff>(
    res => res.json(),
  );

export const loadSolution = createAsyncThunk<
  { solutionInfo: SolutionInfo; diffFileTree: FilesTree },
  number,
  ThunkConfig<string>
>('review/loadSolution', async (solutionId, { rejectWithValue }) => {
  try {
    const solutionInfo = await getSolutionInfo(solutionId);
    const { projectId, sourceCommitHash, targetCommitHash } = solutionInfo;
    const treeDiffInfo = await getFileTreeDiff(projectId, sourceCommitHash, targetCommitHash);

    const diffFileTree = buildFileTree(treeDiffInfo.diffs);

    return {
      solutionInfo,
      diffFileTree,
    };
  } catch (error) {
    return rejectWithValue(String(error));
  }
});
