import { dateTimeParse } from '@gravity-ui/date-utils';
import { Link } from '@gravity-ui/uikit';
import { useAppSelector } from 'app/hooks';
import { StatusCircle } from '../StatusCircle';
import { type HomeworkTableColumn } from './types';

export const useHomeworksColumns = (): HomeworkTableColumn[] => {
  const role = useAppSelector(state => state.user.authData?.role);

  return [
    {
      id: 'name',
      name: () => <span>Название</span>,
      template: ({ name, status }) =>
        role === 'STUDENT' ? (
          <div style={{ display: 'flex', gap: 10 }}>
            <StatusCircle status={status} />
            {name}
          </div>
        ) : (
          name
        ),
      meta: { sort: true },
    },
    {
      id: 'departments',
      name: () => <span>Направление</span>,
      template: ({ departments }) => departments.join(', '),
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
      template: ({ author }) => `${author.firstName} ${author.lastName}`,
      meta: { sort: true },
    },
    {
      id: 'lecture',
      name: () => <span>Лекция</span>,
      template: ({ lecture }) => lecture.name,
      meta: { sort: true },
    },
    {
      id: 'repoLink',
      name: () => <span>Ссылка на репозиторий</span>,
      template: ({ repositoryLink }) => (
        <Link href={repositoryLink} target="_blank" rel="noreferrer">
          Репо
        </Link>
      ),
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
      id: 'reviewDuration',
      name: () => <span>Дедлайн проверки</span>,
      template: ({ reviewDuration }) => `${reviewDuration} ч`,
      meta: { sort: true },
    },
  ];
};
