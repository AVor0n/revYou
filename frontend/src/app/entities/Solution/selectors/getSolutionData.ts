import { type StoreSchema } from 'app';

export const getSolutions = (state: StoreSchema) => state.solution.solutions;
export const getSolutionInfo = (state: StoreSchema) => state.solution.solutionInfo;
