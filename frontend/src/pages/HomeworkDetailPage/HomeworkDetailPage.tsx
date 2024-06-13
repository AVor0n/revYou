import { Skeleton, Tabs } from '@gravity-ui/uikit';
import { useEffect, useMemo } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import { useLazyGetHomeworkQuery, useLazyGetMyReviewsQuery, useLazyGetReviewsToDoQuery } from '@shared/api';
import { useAppSelector } from 'app/hooks';
import { AllSolutionsTab, DescriptionTab, Header, MySolutionsTab, SolutionTab } from './components';
import styles from './HomeworkDetailPage.module.scss';

export const HomeworkDetailPage = () => {
  const { homeworkId, tab } = useParams<{ homeworkId: string; tab: keyof typeof tabs }>();
  const navigate = useNavigate();
  const userInfo = useAppSelector(state => state.user.authData);
  const [loadHomework, { data: homeworkInfo }] = useLazyGetHomeworkQuery();
  const [loadMySolutions, { data: mySolutions }] = useLazyGetMyReviewsQuery();
  const [loadSolutionsForReview, { data: solutionsForReview }] = useLazyGetReviewsToDoQuery();
  const activeTab = tab ?? 'about';

  useEffect(() => {
    if (homeworkId === undefined) return;
    loadHomework({ homeworkId: +homeworkId });
    loadMySolutions({ homeworkId: +homeworkId });
    loadSolutionsForReview({ homeworkId: +homeworkId });
  }, [homeworkId, loadHomework, loadMySolutions, loadSolutionsForReview]);

  const tabs = useMemo(
    () => ({
      about: {
        title: 'Описание',
        content: <DescriptionTab />,
      },
      ...(userInfo?.role === 'TEACHER'
        ? {
            'all-solutions': {
              title: 'Все решения',
              content: <AllSolutionsTab />,
            },
          }
        : {}),
      ...(mySolutions?.data.length
        ? {
            'my-solution': {
              title: 'Мои решения',
              content: <MySolutionsTab />,
            },
          }
        : {}),
      ...(solutionsForReview?.data.length
        ? {
            'for-review': {
              title: 'На проверку',
              content: <SolutionTab />,
            },
          }
        : {}),
    }),
    [mySolutions, solutionsForReview, userInfo],
  );

  return (
    <div className={styles.page}>
      {homeworkInfo ? <Header homeworkName={homeworkInfo.name} /> : <Skeleton className={styles.headerSkeleton} />}

      <div className={styles.pageContent}>
        <Tabs
          className={styles.tabs}
          onSelectTab={tabId => navigate(`/homeworks/${homeworkId}/${tabId}`)}
          activeTab={activeTab}
          items={Object.entries(tabs).map(([id, { title }]) => ({ id, title }))}
        />

        <div className={styles.tabContent}>
          {homeworkInfo ? (
            tabs[activeTab]?.content ?? <Navigate to={`/homeworks/${homeworkId}`} replace />
          ) : (
            <Skeleton className={styles.contentSkeleton} />
          )}
        </div>
      </div>
    </div>
  );
};
