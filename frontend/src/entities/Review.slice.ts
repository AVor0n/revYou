import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import type { Review } from '@shared/api';
import type { FilesTree, FullReviewInfo } from '@shared/types';

export interface ReviewSchema {
  reviewInfo: Review | FullReviewInfo | null;
  filesTree: FilesTree | null;
}

const initialState: ReviewSchema = {
  reviewInfo: null,
  filesTree: null,
};

export const reviewSlice = createSlice({
  name: 'Review',
  initialState,
  reducers: {
    setReviewInfo(state, { payload }: PayloadAction<ReviewSchema['reviewInfo']>) {
      state.reviewInfo = payload;
    },
    setFilesTree(state, { payload }: PayloadAction<ReviewSchema['filesTree']>) {
      state.filesTree = payload;
    },
  },
});

export const { actions: reviewActions, reducer: reviewReducer } = reviewSlice;
