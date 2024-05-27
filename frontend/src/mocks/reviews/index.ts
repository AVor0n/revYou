import { HttpResponse, delay, http } from 'msw';
import { initialReviews } from './initial';
import type { Review } from '@domains';

const reviews = new Map(initialReviews.map(review => [review.reviewId, review]));

export const reviewHandlers = [
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
];
