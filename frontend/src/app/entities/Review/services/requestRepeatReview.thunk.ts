import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const requestRepeatReview = createAsyncThunk<
  null,
  { homeworkId: number; reviewId: number },
  ThunkConfig<string>
>('solution/requestRepeatReview', async ({ homeworkId, reviewId }, { extra, rejectWithValue }) => {
  try {
    await extra.api.uploadCorrections(homeworkId, reviewId);

    return null;
  } catch (e) {
    return rejectWithValue(String(e));
  }
});
