import { createAsyncThunk } from '@reduxjs/toolkit';
import { sortTree } from '@components/Tree';
import { type ThunkConfig } from 'app/providers';
import { isFile, type FilesTree } from '../types';
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

      const diffFileTree = sortTree(buildFileTree(treeDiffInfo.diffs), (itemA, itemB) => {
        if (isFile(itemA) && !isFile(itemB)) {
          return 1;
        }
        if (!isFile(itemA) && isFile(itemB)) {
          return -1;
        }

        return itemA.name.localeCompare(itemB.name);
      });

      return {
        diffFileTree,
      };
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
