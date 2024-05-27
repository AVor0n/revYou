import { Skeleton, Tabs } from '@gravity-ui/uikit';
import { useEffect, useMemo, type ReactNode } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { getReviewInfo, loadReview, useAppDispatch } from 'app';
import { Header, FilesTab, OverviewTab } from './components';
import styles from './ReviewPage.module.scss';

export const ReviewPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { homeworkId, reviewId, role, tab } = useParams<{
    homeworkId: string;
    reviewId: string;
    role: 'student' | 'reviewer';
    tab: keyof typeof tabs;
  }>();
  const reviewInfo = useSelector(getReviewInfo);
  const activeTab = tab ?? 'about';

  useEffect(() => {
    if (reviewInfo) {
      dispatch(loadReview(reviewInfo));
    } else {
      navigate(`/homeworks/${homeworkId}`);
    }
  }, [dispatch, homeworkId, navigate, reviewInfo]);

  const tabs = useMemo(
    () =>
      ({
        about: {
          title: 'Обзор',
          content: reviewInfo && <OverviewTab review={reviewInfo} />,
        },
        files: {
          title: 'Файлы',
          content: <FilesTab />,
        },
      }) satisfies Record<string, { title: string; content: ReactNode }>,
    [reviewInfo],
  );

  return (
    <div className={styles.page}>
      {reviewInfo ? <Header /> : <Skeleton className={styles.headerSkeleton} />}

      <div className={styles.pageContent}>
        <Tabs
          onSelectTab={tabId => navigate(`/homeworks/${homeworkId}/review/${reviewId}/${role}/${tabId}`)}
          activeTab={activeTab}
          items={Object.entries(tabs).map(([id, { title }]) => ({ id, title }))}
        />

        <div className={styles.tabContent}>
          {reviewInfo ? tabs[activeTab].content : <Skeleton className={styles.contentSkeleton} />}
        </div>
      </div>
    </div>
  );
};
