import { Skeleton, Tabs } from '@gravity-ui/uikit';
import { useEffect, useMemo, type ReactNode } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { getSolutionInfo, loadReview, useAppDispatch } from 'app';
import { Header } from './components';
import styles from './SolutionDetailPage.module.scss';

export const SolutionDetailPage = () => {
  const { id: solutionId, tab } = useParams<{ id: string; tab: keyof typeof tabs }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const solutionInfo = useSelector(getSolutionInfo);

  const activeTab = tab ?? 'about';

  useEffect(() => {
    if (solutionId !== undefined) {
      dispatch(loadReview(+solutionId));
    }
  }, [dispatch, solutionId]);

  const tabs = useMemo(
    () =>
      ({
        about: {
          title: 'Описание',
          content: <div>Обзор</div>,
        },
        files: {
          title: 'Файлы',
          content: <div>Изменения</div>,
        },
      }) satisfies Record<string, { title: string; content: ReactNode }>,
    [],
  );

  return (
    <div className={styles.page}>
      <Header>
        {solutionInfo ? `Решение ${solutionInfo.projectId}` : <Skeleton className={styles.headerSkeleton} />}
      </Header>

      <div className={styles.pageContent}>
        <Tabs
          onSelectTab={tabId => navigate(`/solutions/${solutionId}/${tabId}`)}
          activeTab={activeTab}
          items={Object.entries(tabs).map(([id, { title }]) => ({ id, title }))}
        />

        <div className={styles.tabContent}>
          {solutionInfo ? tabs[activeTab].content : <Skeleton className={styles.contentSkeleton} />}
        </div>
      </div>
    </div>
  );
};
