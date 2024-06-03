import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

interface LoadFileOutput {
  filePath: string;
  ref: string;
  content: string;
}

export const loadFile = createAsyncThunk<LoadFileOutput, { filePath: string; ref: string }, ThunkConfig<string>>(
  'review/loadFile',
  async ({ filePath, ref }, { extra, rejectWithValue, getState }) => {
    try {
      const { reviewInfo } = getState().review;

      if (!reviewInfo) {
        throw new Error('Не выбрано ревью');
      }

      const { data } = await extra.api.getRawFile({ filePath, projectId: reviewInfo.projectId, ref });

      return {
        ref,
        filePath,
        content: data,
      };
    } catch (error) {
      toaster.add({
        name: `loadDiff/${filePath}`,
        content: `Не удалось загрузить изменения для файла ${filePath}`,
        theme: 'danger',
      });
      return rejectWithValue(`${filePath}/${ref}`);
    }
  },
  {
    condition: ({ filePath, ref }, { getState }) => {
      const { requestInProgress } = getState().review;
      return !requestInProgress[`loadFile/${filePath}/${ref}`];
    },
  },
);
