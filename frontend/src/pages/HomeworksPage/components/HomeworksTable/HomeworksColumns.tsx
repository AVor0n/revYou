import { dateTimeParse } from '@gravity-ui/date-utils';
import { type HomeworkTableColumn } from './types';

export const homeworksColumns: HomeworkTableColumn[] = [
  {
    id: 'title',
    name: () => <span>Название</span>,
    meta: { sort: true },
  },
  {
    id: 'department',
    name: () => <span>Направление</span>,
    template: ({ department }) => department.join(', '),
  },
  {
    id: 'topic',
    name: () => <span>Тема</span>,
    meta: { sort: true },
  },
  {
    id: 'author',
    name: () => <span>Автор</span>,
    meta: { sort: true },
  },
  {
    id: 'startDate',
    name: () => <span>Дата выдачи</span>,
    template: ({ startDate }) => dateTimeParse(startDate)?.format('DD.MM.YYYY') ?? 'Некорректная дата',
    meta: { sort: true },
  },
  {
    id: 'endDate',
    name: () => <span>Дедлайн</span>,
    template: ({ endDate }) => dateTimeParse(endDate)?.format('DD.MM.YYYY') ?? 'Некорректная дата',
    meta: { sort: true },
  },
];
