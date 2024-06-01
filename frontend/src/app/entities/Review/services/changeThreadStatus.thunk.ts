import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import type { CommentsThreadResolveDto, CommentsThreadStatusEnum } from '@domains';

export const changeThreadStatus = createAsyncThunk<
  { threadId: number; status: CommentsThreadStatusEnum },
  [number, CommentsThreadResolveDto],
  ThunkConfig<string>
>('review/changeThreadStatus', async ([threadId, status], { extra, rejectWithValue }) => {
  try {
    await extra.api.resolveCommentsThread(threadId, status);

    return { threadId, status: status.status as CommentsThreadStatusEnum };
  } catch (e) {
    return rejectWithValue(String(e));
  }
});
