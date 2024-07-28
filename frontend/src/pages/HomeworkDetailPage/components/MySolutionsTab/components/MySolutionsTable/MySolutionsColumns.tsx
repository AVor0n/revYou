import { ReviewStatus } from '@ui';
import { type SolutionTableColumn } from './types';

export const solutionsColumns: SolutionTableColumn[] = [
  {
    id: 'reviewId',
    name: () => <span>Ревью</span>,
    template: ({ reviewId }) => reviewId,
    placeholder: 'Неизвестное ревью',
    meta: { sort: true },
  },
  {
    id: 'status',
    name: () => <span>Статус</span>,
    template: ({ status }) => <ReviewStatus status={status} />,
    width: 200,
    meta: { sort: true },
  },
];
