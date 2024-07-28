import { Skeleton } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { useMatch, useNavigate } from 'react-router-dom';
import { useGetHomeworksQuery, useLazyGetHomeworkQuery } from '@shared/api';
import { useApiError } from '@shared/utils';
import { CreateHomeworkWindow, EditHomeworkWindow, HomeworksTable, HomeworksToolbar } from './components';
import styles from './HomeworksPage.module.scss';

export const HomeworksPage = () => {
  const navigate = useNavigate();
  const showCreateWindow = useMatch('/homeworks/create');
  const showEditWindow = useMatch('/homeworks/:id/edit');

  const [search, setSearch] = useState('');
  const { data: homeworks, error: homeworksError } = useGetHomeworksQuery();
  useApiError(homeworksError, { name: 'loadHomeworks', title: 'Не удалось загрузить список домашних работ' });

  const [loadHomeworkForEdit, { data: homeworkForEdit, error: homeworkError }] = useLazyGetHomeworkQuery();
  useApiError(homeworkError, { name: 'loadHomework', title: 'Ошибка загрузки домашней работы' });

  const filteredHomeworks = homeworks?.data.filter(homework => homework.name.includes(search));

  useEffect(() => {
    const id = showEditWindow?.params.id;
    if (id === undefined) return;
    loadHomeworkForEdit({ homeworkId: +id });
  }, [loadHomeworkForEdit, showEditWindow?.params.id]);

  const openCreateWindow = () => {
    navigate('/homeworks/create');
  };

  return (
    <div className={styles.page}>
      <HomeworksToolbar
        search={search}
        onSearch={setSearch}
        disabled={!filteredHomeworks}
        onCreate={openCreateWindow}
      />
      {filteredHomeworks ? (
        <HomeworksTable data={filteredHomeworks} onRowClick={({ id }) => navigate(`/homeworks/${id}`)} />
      ) : (
        <Skeleton className={styles.skeleton} />
      )}
      <CreateHomeworkWindow open={!!showCreateWindow} />
      <EditHomeworkWindow open={!!showEditWindow} record={homeworkForEdit} />
    </div>
  );
};
