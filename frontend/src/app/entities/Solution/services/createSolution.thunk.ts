import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AxiosError } from 'axios';
import { type Solution } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const createSolution = createAsyncThunk<
  Solution,
  { homeworkId: number; branchLink: string },
  ThunkConfig<string>
>('solution/createSolution', async ({ homeworkId, branchLink }, { extra, rejectWithValue }) => {
  try {
    const { data } = await extra.api.createSolution(homeworkId, { branchLink });
    await extra.api.createReview(homeworkId);

    return data;
  } catch (e) {
    const error = (e as AxiosError).response?.data as { message: string };
    toaster.add({ name: 'createSolution', title: 'Ошибка отправки решения', content: error.message, theme: 'danger' });
    return rejectWithValue(error.message);
  }
});
