import { Skeleton } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useMatch, useNavigate } from 'react-router-dom';
import { getHomeworkForEdit, getHomeworks, homeworkActions, selectHomework, loadHomeworks, useAppDispatch } from 'app';
import { CreateHomeworkWindow, EditHomeworkWindow, HomeworksTable, HomeworksToolbar } from './components';
import styles from './HomeworksPage.module.scss';

export const HomeworksPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const showCreateWindow = useMatch('/homeworks/create');
  const showEditWindow = useMatch('/homeworks/:id/edit');

  const [search, setSearch] = useState('');
  const homeworks = useSelector(getHomeworks);
  const homeworkForEdit = useSelector(getHomeworkForEdit);

  const filteredHomeworks = homeworks?.filter(homework => homework.name?.includes(search));

  useEffect(() => {
    const id = showEditWindow?.params.id;
    if (id) {
      dispatch(selectHomework(+id));
    }
  }, [dispatch, showEditWindow?.params.id]);

  useEffect(() => {
    dispatch(loadHomeworks());
  }, [dispatch]);

  const openCreateWindow = () => {
    dispatch(homeworkActions.setHomeworkForEdit({}));
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
