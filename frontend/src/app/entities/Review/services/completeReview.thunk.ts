import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import type { ReviewResolutionDtoStatusEnum } from '@domains';

export const completeReview = createAsyncThunk<
  ReviewResolutionDtoStatusEnum,
  { homeworkId: number; reviewId: number; comment: string; status: ReviewResolutionDtoStatusEnum },
  ThunkConfig<string>
>('solution/completeReview', async ({ homeworkId, reviewId, comment, status }, { extra, rejectWithValue }) => {
  try {
    await extra.api.addReviewResolution(homeworkId, reviewId, {
      resolution: comment,
      status,
    });

    return status;
  } catch (e) {
    return rejectWithValue(status);
  }
});
