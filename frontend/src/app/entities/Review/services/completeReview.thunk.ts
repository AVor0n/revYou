import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const completeReview = createAsyncThunk<
  null,
  { homeworkId: number; reviewId: number; comment: string; status: 'APPROVED' | 'CORRECTIONS_REQUIRED' },
  ThunkConfig<string>
>('solution/completeReview', async ({ homeworkId, reviewId, comment, status }, { extra, rejectWithValue }) => {
  try {
    await extra.api.addReviewResolution(homeworkId, reviewId, {
      resolution: comment,
      status,
    });

    return null;
  } catch (e) {
    return rejectWithValue(String(e));
  }
});
