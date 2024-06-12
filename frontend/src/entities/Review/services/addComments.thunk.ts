import { createAsyncThunk } from '@reduxjs/toolkit';
import { type CommentPostDto, type Comment } from '@api';
import { type ThunkConfig } from 'app/providers';

export const addComment = createAsyncThunk<
  { threadId: number; comment: Comment },
  { threadId: number; comment: CommentPostDto },
  ThunkConfig<string>
>('review/addComment', async ({ threadId, comment }, { extra, rejectWithValue }) => {
  try {
    const { data } = await extra.api.addComment(threadId, comment);
    return {
      threadId,
      comment: data,
    };
  } catch (e) {
    return rejectWithValue(String(e));
  }
});
