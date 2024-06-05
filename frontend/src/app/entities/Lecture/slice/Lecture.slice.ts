import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { loadLectures } from '../services/loadLectures.thunk';
import { type LectureSchema } from '../types/Lecture.types';

const initialState: LectureSchema = {
  lectures: [],
  error: '',
};

export const LectureSlice = createSlice({
  name: 'Lecture',
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder.addCase(loadLectures.fulfilled, (state, { payload }) => {
      state.lectures = payload;
    });
    builder.addCase(loadLectures.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: lectureActions, reducer: lectureReducer } = LectureSlice;
