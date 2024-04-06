import { Skeleton } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { getHomeworks, loadHomeworks, useAppDispatch } from 'app';
import { HomeworksTable, HomeworksToolbar } from './components';
import styles from './HomeworksPage.module.scss';

export const HomeworksPage = () => {
  const [search, setSearch] = useState('');
  const homeworks = useSelector(getHomeworks);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(loadHomeworks());
  }, [dispatch]);

  const filteredHomeworks = homeworks?.filter(homework => homework.name?.includes(search));

  return (
    <div className={styles.page}>
      <HomeworksToolbar search={search} onSearch={setSearch} disabled={!filteredHomeworks} />
      {filteredHomeworks ? <HomeworksTable data={filteredHomeworks} /> : <Skeleton className={styles.skeleton} />}
    </div>
  );
};
