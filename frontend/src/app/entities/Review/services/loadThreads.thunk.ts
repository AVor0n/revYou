import { createAsyncThunk } from '@reduxjs/toolkit';
import { type CommentsThread } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadThreads = createAsyncThunk<CommentsThread[], number, ThunkConfig<string>>(
  'review/loadThreads',
  async (reviewId, { extra, rejectWithValue, getState }) => {
    try {
      const { reviewInfo } = getState().review;

      if (!reviewInfo) {
        throw new Error('Нет информации о выбранном ревью');
      }

      const { data } = await extra.api.getAllThreads(reviewId);

      return data.data.map(thread => ({
        ...thread,
        sourceSha: reviewInfo.sourceCommitId,
        targetSha: reviewInfo.commitId,
      }));
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
