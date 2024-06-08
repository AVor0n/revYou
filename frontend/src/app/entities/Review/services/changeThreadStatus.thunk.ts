import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import type { CommentsThreadResolveDto, CommentsThreadStatus } from '@domains';

export const changeThreadStatus = createAsyncThunk<
  { threadId: number; status: CommentsThreadStatus },
  [number, CommentsThreadResolveDto],
  ThunkConfig<string>
>('review/changeThreadStatus', async ([threadId, status], { extra, rejectWithValue }) => {
  try {
    await extra.api.resolveCommentsThread(threadId, status);

    return { threadId, status: status.status };
  } catch (e) {
    return rejectWithValue(String(e));
  }
});
