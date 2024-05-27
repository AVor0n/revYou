import { Skeleton, Tabs } from '@gravity-ui/uikit';
import { useEffect, useMemo } from 'react';
import { useSelector } from 'react-redux';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import {
  getMySolutions,
  getSelectedHomework,
  getSolutionsForReview,
  loadMySolutions,
  loadSolutionsForReview,
  selectHomeworkForView,
  useAppDispatch,
} from 'app';
import { DescriptionTab, Header, MySolutionsTab, SolutionTab } from './components';
import styles from './HomeworkDetailPage.module.scss';

export const HomeworkDetailPage = () => {
  const { homeworkId, tab } = useParams<{ homeworkId: string; tab: keyof typeof tabs }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const homeworkInfo = useSelector(getSelectedHomework);
  const mySolutions = useSelector(getMySolutions);
  const solutionsForReview = useSelector(getSolutionsForReview);
  const activeTab = tab ?? 'about';

  useEffect(() => {
    if (homeworkId !== undefined) {
      dispatch(selectHomeworkForView(+homeworkId));
      dispatch(loadMySolutions(+homeworkId));
      dispatch(loadSolutionsForReview(+homeworkId));
    }
  }, [dispatch, homeworkId]);

  const tabs = useMemo(
    () => ({
      about: {
        title: 'Описание',
        content: <DescriptionTab homeworkInfo={homeworkInfo} />,
      },
      ...(mySolutions?.length
        ? {
            'my-solution': {
              title: 'Мои решения',
              content: <MySolutionsTab homeworkInfo={homeworkInfo} />,
            },
          }
        : {}),
      ...(solutionsForReview?.length
        ? {
            'for-review': {
              title: 'На проверку',
              content: <SolutionTab homeworkInfo={homeworkInfo} />,
            },
          }
        : {}),
    }),
    [homeworkInfo, mySolutions, solutionsForReview],
  );

  return (
    <div className={styles.page}>
      {homeworkInfo ? <Header homeworkName={homeworkInfo.name} /> : <Skeleton className={styles.headerSkeleton} />}

      <div className={styles.pageContent}>
        <Tabs
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
