import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const loadFileDiff = createAsyncThunk<
  { sourceFileContent: string; targetFileContent: string },
  { projectId: number; fromRef: string; toRef: string; path: string },
  ThunkConfig<string>
>('review/loadFileDiff', async ({ path, projectId, fromRef, toRef }, { extra, rejectWithValue }) => {
  try {
    const [sourceFileContent, targetFileContent] = (
      await Promise.allSettled([
        extra.api.getRawFile({ filePath: path, projectId, ref: fromRef }),
        extra.api.getRawFile({ filePath: path, projectId, ref: toRef }),
      ])
    ).map(response => (response.status === 'fulfilled' ? response.value.data : ''));

    return {
      sourceFileContent,
      targetFileContent,
    };
  } catch (error) {
    toaster.add({
      name: `loadDiff/${path}`,
      content: `Не удалось загрузить изменения для файла ${path}`,
      theme: 'danger',
    });
    return rejectWithValue(String(error));
  }
});
