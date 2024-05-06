import { dateTimeParse } from '@gravity-ui/date-utils';
import { Label } from '@gravity-ui/uikit';
import { type SolutionTableColumn } from './types';

export const solutionsColumns: SolutionTableColumn[] = [
  {
    id: 'projectId',
    name: () => <span>Номер решения</span>,
    template: ({ projectId }) => projectId,
    placeholder: 'Неизвестное решение',
    meta: { sort: true },
  },
  {
    id: 'reviewScore',
    name: () => <span>Количество ревью</span>,
    template: ({ reviewScore }) => reviewScore,
    placeholder: 'Работа не оценена',
    meta: { sort: true },
  },
  {
    id: 'approveScore',
    name: () => <span>Количество утверждений</span>,
    template: ({ approveScore }) => approveScore,
    placeholder: 'Работа не оценена',
    meta: { sort: true },
  },
  {
    id: 'solutionAttempts',
    name: () => <span>Количество попыток</span>,
    template: ({ solutionAttempts }) => solutionAttempts.length,
    placeholder: 'Попыток не было',
    meta: { sort: true },
  },
  {
    id: 'lastAttempt',
    name: () => <span>Последняя попытка</span>,
    template: ({ solutionAttempts }) => {
      const lastAttemtDate = solutionAttempts.at(-1)?.createdAt;
      if (!lastAttemtDate) return 'Попыток не было';
      return dateTimeParse(lastAttemtDate)?.format('DD.MM.YYYY');
    },
    meta: { sort: true },
  },
  {
    id: 'status',
    name: () => <span>Статус</span>,
    template: ({ status }) => {
      if (status === 'review') return <Label theme="utility">Ревью</Label>;
      if (status === 'search_reviewer') return <Label theme="warning">Поиск ревьюера</Label>;
      return <Label theme="info">Ожидание исправлений</Label>;
    },
    meta: { sort: true },
  },
];
