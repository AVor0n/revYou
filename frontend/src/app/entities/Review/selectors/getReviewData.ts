import { type StoreSchema } from 'app';

export const getReviewInfo = (state: StoreSchema) => state.review.reviewInfo;
export const getActiveFilePath = (state: StoreSchema) => state.review.activeFilePath;
export const getFilesTree = (state: StoreSchema) => state.review.filesTree;
export const getFilesCache = (state: StoreSchema) => state.review.filesCache;
export const getCommentThreads = (state: StoreSchema) => state.review.threads;
export const getRequestInProgress = (state: StoreSchema) => state.review.requestInProgress;
