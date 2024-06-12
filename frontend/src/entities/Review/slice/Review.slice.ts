import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import {
  addComment,
  changeThreadStatus,
  completeReview,
  createThread,
  loadFile,
  loadReview,
  loadThreads,
  requestRepeatReview,
  startReview,
} from '../services';
import { type ReviewSchema } from '../types';

const initialState: ReviewSchema = {
  filesCache: {},
  reviewInfo: null,
  filesTree: null,
  threads: [],
  requestInProgress: {},
  error: '',
};

export const reviewSlice = createSlice({
  name: 'Review',
  initialState,
  reducers: {
    setReviewInfo(state, { payload }: PayloadAction<ReviewSchema['reviewInfo']>) {
      state.reviewInfo = payload;
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
    clear() {
      return initialState;
    },
  },
  extraReducers(builder) {
    builder.addCase(startReview.fulfilled, state => {
      state.requestInProgress.startReview = false;
    });
    builder.addCase(startReview.pending, state => {
      state.requestInProgress.startReview = true;
    });
    builder.addCase(startReview.rejected, state => {
      state.requestInProgress.startReview = false;
    });
    builder.addCase(completeReview.fulfilled, (state, { payload }) => {
      if (payload === 'APPROVED') state.requestInProgress.approveSolution = false;
      if (payload === 'CORRECTIONS_REQUIRED') state.requestInProgress.rejectSolution = false;
    });
    builder.addCase(completeReview.rejected, (state, { payload }) => {
      if (payload === 'APPROVED') state.requestInProgress.approveSolution = false;
      if (payload === 'CORRECTIONS_REQUIRED') state.requestInProgress.rejectSolution = false;
    });
    builder.addCase(loadReview.fulfilled, (state, { payload }) => {
      state.filesTree = payload.diffFileTree;
      state.error = '';
    });
    builder.addCase(loadReview.rejected, state => {
      state.reviewInfo = null;
      state.error = 'Не удалось загрузить ревью';
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
    builder.addCase(loadThreads.rejected, state => {
      state.threads = null;
      state.error = 'Не удалось загрузить комментарии';
    });
    builder.addCase(requestRepeatReview.fulfilled, state => {
      state.requestInProgress.requestRepeatReview = false;
      state.reviewInfo = null;
    });
    builder.addCase(requestRepeatReview.pending, state => {
      state.requestInProgress.requestRepeatReview = true;
    });
    builder.addCase(requestRepeatReview.rejected, state => {
      state.requestInProgress.requestRepeatReview = false;
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
  },
});

export const { actions: reviewActions, reducer: reviewReducer } = reviewSlice;
