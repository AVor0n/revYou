import { createAsyncThunk } from '@reduxjs/toolkit';
import { loadSolution } from 'app';
import { type ThunkConfig } from 'app/providers';
import { type FilesTree } from '../types';
import { buildFileTree } from './buildFileTree';

export const loadReview = createAsyncThunk<{ diffFileTree: FilesTree }, number, ThunkConfig<string>>(
  'review/loadReview',
  async (solutionId, { extra, rejectWithValue, dispatch }) => {
    try {
      const solutionInfo = (await dispatch(loadSolution(solutionId))).payload;
      if (solutionInfo === undefined || typeof solutionInfo === 'string') {
        throw new Error(`No required data`);
      }
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
        diffFileTree,
      };
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
