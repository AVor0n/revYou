import { dateTimeParse } from '@gravity-ui/date-utils';
import { type HomeworkTableColumn } from './types';

export const homeworksColumns: HomeworkTableColumn[] = [
  {
    id: 'name',
    name: () => <span>Название</span>,
    meta: { sort: true },
  },
  {
    id: 'departments',
    name: () => <span>Направление</span>,
    template: ({ departments }) => departments?.join(', '),
    placeholder: 'Не задано',
  },
  {
    id: 'topic',
    name: () => <span>Тема</span>,
    meta: { sort: true },
  },
  {
    id: 'author',
    name: () => <span>Автор</span>,
    template: ({ author }) => `${author?.firstName ?? ''} ${author?.lastName ?? ''}`,
    meta: { sort: true },
  },
  {
    id: 'lecture',
    name: () => <span>Лекция</span>,
    template: ({ lecture }) => lecture?.name,
    meta: { sort: true },
  },
  {
    id: 'repoLink',
    name: () => <span>Ссылка на репозиторий</span>,
    template: ({ repositoryLink }) => <a href={repositoryLink}>Репо</a>,
    meta: { sort: true },
  },
  {
    id: 'startDate',
    name: () => <span>Дата выдачи</span>,
    template: ({ startDate }) => dateTimeParse(startDate)?.format('DD.MM.YYYY'),
    placeholder: 'Некорректная дата',
    meta: { sort: true },
  },
  {
    id: 'completionDeadline',
    name: () => <span>Дедлайн выполнения</span>,
    template: ({ completionDeadline }) => dateTimeParse(completionDeadline)?.format('DD.MM.YYYY'),
    placeholder: 'Некорректная дата',
    meta: { sort: true },
  },
  {
    // FIXME: #back
    // опечатка в api reviewDuraion -> reviewDuration
    id: 'reviewDuraion',
    name: () => <span>Дедлайн проверки</span>,
    template: ({ reviewDuraion }) => `${reviewDuraion} ч`,
    meta: { sort: true },
  },
];
