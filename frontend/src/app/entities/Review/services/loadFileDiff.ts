import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

const getFileContentByPath = (projectId: string, ref: string, path: string) =>
  fetch(
    `https://gitlab.com/api/v4/projects/${projectId}/repository/files/${encodeURIComponent(path.replace(/^\//, ''))}/raw?ref=${ref}`,
  ).then<string>(res => {
    if (res.status >= 200 && res.status < 400) {
      return res.text();
    }
    return '';
  });

export const loadFileDiff = createAsyncThunk<
  { sourceFileContent: string; targetFileContent: string },
  { projectId: string; fromRef: string; toRef: string; path: string },
  ThunkConfig<string>
>('review/loadFileDiff', async ({ path, projectId, fromRef, toRef }, { rejectWithValue }) => {
  try {
    const [sourceFileContent, targetFileContent] = (
      await Promise.allSettled([
        getFileContentByPath(projectId, fromRef, path),
        getFileContentByPath(projectId, toRef, path),
      ])
    ).map(response => (response.status === 'fulfilled' ? response.value : ''));

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
