import { type StoreSchema } from 'app';

export const getMySolutions = (state: StoreSchema) => state.solution.mySolutions;
export const getSolutionsForReview = (state: StoreSchema) => state.solution.solutionsForReview;
