import { useState } from 'react';
import { HomeworksTable, HomeworksToolbar } from './components';
import { mockHomeworks } from './constants';
import styles from './HomeworksPage.module.scss';

export const HomeworksPage = () => {
  const [search, setSearch] = useState('');

  const filteredHomeworks = mockHomeworks.filter(homework => homework.title.includes(search));

  return (
    <div className={styles.page}>
      <HomeworksToolbar search={search} onSearch={setSearch} />
      <HomeworksTable data={filteredHomeworks} />
    </div>
  );
};
