import { ReviewStatus } from '@ui';
import { StartReviewButton } from '../StartReviewButton/StartReviewButton';
import { type SolutionTableColumn } from './types';

export const getSolutionsColumns = (homeworkId: number): SolutionTableColumn[] => [
  {
    id: 'reviewId',
    name: () => <span>Решение</span>,
    template: ({ reviewId }) => reviewId,
    placeholder: 'Неизвестное решение',
    meta: { sort: true },
  },
  {
    id: 'status',
    name: () => <span>Статус</span>,
    template: ({ status, reviewId }) => {
      if (status === 'REVIEWER_FOUND') return <StartReviewButton homeworkId={homeworkId} reviewId={reviewId} />;
      return <ReviewStatus status={status} />;
    },
    width: 200,
    meta: { sort: true },
  },
];
