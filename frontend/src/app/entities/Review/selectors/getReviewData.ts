import { type StoreSchema } from 'app';

export const getReviewInfo = (state: StoreSchema) => state.review.reviewInfo;
export const getActiveFilePath = (state: StoreSchema) => state.review.activeFilePath;
export const getFilesTree = (state: StoreSchema) => state.review.filesTree;
export const getSourceActiveFileContent = (state: StoreSchema) => state.review.sourceActiveFileContent;
export const getTargetActiveFileContent = (state: StoreSchema) => state.review.targetActiveFileContent;
