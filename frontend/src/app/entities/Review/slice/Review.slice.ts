import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import {
  addComment,
  changeThreadStatus,
  createThread,
  loadFile,
  loadReview,
  loadThreads,
  requestRepeatReview,
} from '../services';
import { type ReviewSchema } from '../types';

const initialState: ReviewSchema = {
  filesCache: {},
  reviewInfo: null,
  filesTree: null,
  activeFilePath: '',
  threads: [],
  requestInProgress: {},
  error: '',
};

export const reviewSlice = createSlice({
  name: 'Review',
  initialState,
  reducers: {
    setActiveFilePath(state, { payload }: PayloadAction<ReviewSchema['activeFilePath']>) {
      if (payload === state.activeFilePath) return;

      state.activeFilePath = payload;
    },
    setReviewInfo(state, { payload }: PayloadAction<ReviewSchema['reviewInfo']>) {
      state.reviewInfo = payload;
      state.activeFilePath = '';
      state.threads = null;
    },
    addRequestInProgress(state, { payload }: PayloadAction<string>) {
      state.requestInProgress[payload] = true;
    },
    addFileContent(state, { payload }: PayloadAction<{ path: string; ref: string; content: string | null }>) {
      if (!state.filesCache[payload.path]) {
        state.filesCache[payload.path] = {};
      }
      state.filesCache[payload.path]![payload.ref] = payload.content;
    },
  },
  extraReducers(builder) {
    builder.addCase(loadReview.fulfilled, (state, { payload }) => {
      state.filesTree = payload.diffFileTree;
      state.error = '';
    });
    builder.addCase(loadFile.fulfilled, (state, { payload }) => {
      const { filePath, ref, content } = payload;
      state.requestInProgress[`loadFile/${filePath}/${ref}`] = false;

      if (!state.filesCache[filePath]) {
        state.filesCache[filePath] = {};
      }

      state.filesCache[filePath]![ref] = content;
    });
    builder.addCase(loadFile.rejected, (state, { payload }) => {
      state.requestInProgress[`loadFile/${payload}`] = false;
    });
    builder.addCase(loadThreads.fulfilled, (state, { payload }) => {
      state.threads = payload;
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
      state.requestInProgress.createThread = false;
      if (!state.threads) {
        state.threads = [];
      }
      state.threads.push(payload);
    });
    builder.addCase(createThread.pending, state => {
      state.requestInProgress.createThread = true;
    });
    builder.addCase(createThread.rejected, state => {
      state.requestInProgress.createThread = false;
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
