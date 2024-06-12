import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ReviewerChange } from '@api';
import { type ThunkConfig } from 'app/providers';

export const changeReviewer = createAsyncThunk<
  null,
  { homeworkId: number; reviewer: ReviewerChange },
  ThunkConfig<string>
>('solution/changeReviewer', async ({ homeworkId, reviewer }, { extra, rejectWithValue }) => {
  try {
    await extra.api.replaceReviewer(homeworkId, reviewer);
    return null;
  } catch (error) {
    return rejectWithValue(String(error));
  }
});
