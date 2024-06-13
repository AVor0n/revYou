import { Skeleton } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { useMatch, useNavigate } from 'react-router-dom';
import { useGetHomeworksQuery, useLazyGetHomeworkQuery } from '@shared/api';
import { CreateHomeworkWindow, EditHomeworkWindow, HomeworksTable, HomeworksToolbar } from './components';
import styles from './HomeworksPage.module.scss';

export const HomeworksPage = () => {
  const navigate = useNavigate();
  const showCreateWindow = useMatch('/homeworks/create');
  const showEditWindow = useMatch('/homeworks/:id/edit');

  const [search, setSearch] = useState('');
  const { data: homeworks } = useGetHomeworksQuery();
  const [loadHomeworkForEdit, { data: homeworkForEdit }] = useLazyGetHomeworkQuery();

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
