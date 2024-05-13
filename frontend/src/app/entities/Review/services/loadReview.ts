import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import { type FilesTree } from '../types';
import { buildFileTree } from './buildFileTree';
import type { Review } from '@domains';

export const loadReview = createAsyncThunk<{ diffFileTree: FilesTree }, Review, ThunkConfig<string>>(
  'review/loadReview',
  async (review, { extra, rejectWithValue }) => {
    try {
      const { projectId, sourceCommitId, commitId } = review;
      if (!projectId || !sourceCommitId || !commitId) {
        throw new Error('no required params');
      }

      const { data: treeDiffInfo } = await extra.api.getDiffs({
        projectId,
        from: sourceCommitId,
        to: commitId,
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
