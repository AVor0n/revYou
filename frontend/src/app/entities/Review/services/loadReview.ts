import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import { type FilesTree } from '../types';
import { buildFileTree } from './buildFileTree';
import type { Solution } from '@domains';

export const loadReview = createAsyncThunk<
  { solutionInfo: Solution; diffFileTree: FilesTree },
  number,
  ThunkConfig<string>
>('review/loadReview', async (solutionId, { extra, rejectWithValue }) => {
  try {
    const { data: solutionInfo } = await extra.api.readSolution(solutionId);
    const { projectId } = solutionInfo;
    const sourceCommitHash = solutionInfo.sourceCommitId;
    const targetCommitHash = solutionInfo.solutionAttempts.at(-1)?.commitId;

    if (!projectId || !targetCommitHash) {
      throw new Error(`No required data`);
    }

    const { data: treeDiffInfo } = await extra.api.getDiffs({
      projectId,
      from: sourceCommitHash,
      to: targetCommitHash,
    });

    const diffFileTree = buildFileTree(treeDiffInfo.diffs);

    return {
      solutionInfo,
      diffFileTree,
    };
  } catch (error) {
    return rejectWithValue(String(error));
  }
});
