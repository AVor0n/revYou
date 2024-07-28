import { HttpResponse, delay, http } from 'msw';
import { initialReviews, initialThreads } from './initial';
import type {
  Review,
  ThreadWrapper,
  Comment,
  ThreadPost,
  CommentsThread,
  CommentPostDto,
  ReviewInfoWrapper,
} from '@api';

const reviews = new Map(initialReviews.map(review => [review.reviewId, review]));
const threads = new Map(initialThreads.map(thread => [thread.threadId, thread]));

export const reviewHandlers = [
  http.get<{ id: string }, Record<string, never>, ReviewInfoWrapper>(
    '/api/homeworks/:id/reviews/reviews-info',
    async () => {
      await delay(300);

      return HttpResponse.json({
        data: Array.from(reviews.values()).map((review, idx) => ({
          ...review,
          reviewAttempts: review.reviewAttempts ?? [],
          student: {
            userId: idx,
            username: 'username',
          },

          reviewer: {
            userId: idx,
            username: 'username',
          },
          duration: {
            hours: Math.floor(Math.random() * 100),
            minutes: Math.floor(Math.random() * 100),
          },
        })),
        sourceCommitId: 'main',
      });
    },
  ),
  http.get<{ homeworkId: string }, Record<string, never>, { data: Review[] }>(
    '/api/homeworks/:homeworkId/reviews/my-reviews',
    async () => {
      await delay(300);
      return HttpResponse.json({
        data: Array.from(reviews.values()),
      });
    },
  ),
  http.get<{ homeworkId: string }, Record<string, never>, { data: Review[] }>(
    '/api/homeworks/:homeworkId/reviews/reviews-to-do',
    async () => {
      await delay(300);
      return HttpResponse.json({
        data: Array.from(reviews.values()),
      });
    },
  ),
  http.get<{ reviewId: string }, Record<string, never>, ThreadWrapper>('/api/threads/review/:reviewId', async () => {
    await delay(300);
    return HttpResponse.json({
      data: Array.from(threads.values()),
    });
  }),
  http.post<{ threadId: string }, CommentPostDto, Comment>(
    '/api/threads/:threadId/comments',
    async ({ params, request }) => {
      const { threadId } = params;
      const comment = await request.json();

      const thread = threads.get(+threadId);
      if (!thread) {
        return HttpResponse.json(null, { status: 404 });
      }

      const newComment: Comment = {
        commentId: Math.floor(Math.random() * 100),
        authorId: 1,
        content: comment.content,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      };

      thread.comments.push(newComment);

      return HttpResponse.json(newComment);
    },
  ),
  http.post<Record<string, never>, ThreadPost, CommentsThread>('/api/threads', async ({ request }) => {
    const { content = '', ...thread } = await request.json();

    const newThread: CommentsThread = {
      ...thread,
      threadId: Math.floor(Math.random() * 100),
      authorId: 1,
      status: 'ACTIVE',
      comments: [
        {
          commentId: Math.floor(Math.random() * 100),
          authorId: 1,
          content,
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
        },
      ],
    };

    threads.set(newThread.threadId, newThread);

    return HttpResponse.json(newThread);
  }),
  http.patch<{ threadId: string }, Record<string, never>, null>('/api/threads/:threadId/resolve', ({ params }) => {
    const { threadId } = params;

    const thread = threads.get(+threadId);
    if (!thread) {
      return HttpResponse.json(null, { status: 404 });
    }

    thread.status = 'RESOLVED';

    return HttpResponse.json(null);
  }),
];
