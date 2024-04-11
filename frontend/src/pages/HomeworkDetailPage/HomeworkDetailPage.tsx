import { Skeleton, Tabs } from '@gravity-ui/uikit';
import { useEffect, type ReactNode } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { getSelectedHomework, homeworkActions, loadHomework, useAppDispatch } from 'app';
import { Header } from './components';
import styles from './HomeworkDetailPage.module.scss';

const tabs = {
  about: {
    title: 'Описание',
    content: <div>About</div>,
  },
  solution: {
    title: 'Мое решение',
    content: <div>Solution</div>,
  },
  solutions: {
    title: 'Решения',
    content: <div>Solutions</div>,
  },
} satisfies Record<string, { title: string; content: ReactNode }>;

export const HomeworkDetailPage = () => {
  const { id: homeworkId, tab } = useParams<{ id: string; tab: keyof typeof tabs }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const homeworkInfo = useSelector(getSelectedHomework);
  const activeTab = tab ?? 'about';

  useEffect(() => {
    if (homeworkId !== undefined) {
      dispatch(loadHomework(+homeworkId))
        .unwrap()
        .then(homework => dispatch(homeworkActions.setSelectedHomework(homework)));
    }
  }, [dispatch, homeworkId]);

  return (
    <div className={styles.page}>
      <Header>{homeworkInfo ? homeworkInfo.name : <Skeleton className={styles.headerSkeleton} />}</Header>

      <div className={styles.pageContent}>
        <Tabs
          onSelectTab={tabId => navigate(`/homeworks/${homeworkId}/${tabId}`)}
          activeTab={activeTab}
          items={Object.entries(tabs).map(([id, { title }]) => ({ id, title }))}
        />

        <div className={styles.tabContent}>
          {homeworkInfo ? tabs[activeTab].content : <Skeleton className={styles.contentSkeleton} />}
        </div>
      </div>
    </div>
  );
};
