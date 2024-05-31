import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import {
  addComment,
  changeThreadStatus,
  createThread,
  loadFileDiff,
  loadReview,
  loadThreads,
  requestRepeatReview,
} from '../services';
import { type ReviewSchema } from '../types';

const initialState: ReviewSchema = {
  reviewInfo: null,
  filesTree: null,
  activeFilePath: '',
  sourceActiveFileContent: null,
  targetActiveFileContent: null,
  threads: [],
  createThreadInProgress: false,
  error: '',
};

export const reviewSlice = createSlice({
  name: 'Review',
  initialState,
  reducers: {
    setActiveFilePath(state, { payload }: PayloadAction<ReviewSchema['activeFilePath']>) {
      if (payload === state.activeFilePath) return;

      state.activeFilePath = payload;
      state.sourceActiveFileContent = null;
      state.targetActiveFileContent = null;
    },
    setReviewInfo(state, { payload }: PayloadAction<ReviewSchema['reviewInfo']>) {
      state.reviewInfo = payload;
      state.activeFilePath = '';
      state.sourceActiveFileContent = null;
      state.targetActiveFileContent = null;
      state.threads = null;
    },
  },
  extraReducers(builder) {
    builder.addCase(loadReview.fulfilled, (state, { payload }) => {
      state.filesTree = payload.diffFileTree;
      state.error = '';
    });
    builder.addCase(loadFileDiff.fulfilled, (state, { payload }) => {
      state.sourceActiveFileContent = payload.sourceFileContent;
      state.targetActiveFileContent = payload.targetFileContent;
    });
    builder.addCase(loadThreads.fulfilled, (state, { payload }) => {
      state.threads = payload;
    });
    builder.addCase(loadFileDiff.rejected, state => {
      state.activeFilePath = '';
      state.sourceActiveFileContent = null;
      state.targetActiveFileContent = null;
    });
    builder.addCase(requestRepeatReview.fulfilled, state => {
      state.reviewInfo = null;
    });
    builder.addCase(addComment.fulfilled, (state, { payload }) => {
      if (!state.threads) return;
      state.threads = state.threads.map(thread => {
        if (thread.threadId === payload.threadId) {
          thread.comments.push(payload.comment);
        }
        return thread;
      });
    });
    builder.addCase(changeThreadStatus.fulfilled, (state, { payload }) => {
      if (!state.threads) return;
      state.threads = state.threads.map(thread => {
        if (thread.threadId === payload.threadId) {
          thread.status = payload.status;
        }
        return thread;
      });
    });
    builder.addCase(createThread.fulfilled, (state, { payload }) => {
      state.createThreadInProgress = false;
      if (!state.threads) {
        state.threads = [];
      }
      state.threads.push(payload);
    });
    builder.addCase(createThread.pending, state => {
      state.createThreadInProgress = true;
    });
    builder.addCase(createThread.rejected, state => {
      state.createThreadInProgress = false;
      state.threads = null;
      state.error = 'Не удалось создать тред';
    });
    builder.addCase(loadThreads.rejected, state => {
      state.threads = null;
      state.error = 'Не удалось загрузить комментарии';
    });
    builder.addCase(loadReview.rejected, state => {
      state.reviewInfo = null;
      state.error = 'Не удалось загрузить ревью';
    });
  },
});

export const { actions: reviewActions, reducer: reviewReducer } = reviewSlice;
