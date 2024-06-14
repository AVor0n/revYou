import { Tabs } from '@gravity-ui/uikit';
import { useEffect, useMemo, type ReactNode } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { reviewActions } from '@entities';
import { useLazyGetDiffsQuery } from '@shared/api';
import { isFile } from '@shared/types';
import { sortTree } from '@shared/ui';
import { useAppDispatch, useAppSelector } from 'app/hooks';
import { buildFileTree } from 'entities/Review/services/buildFileTree';
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
  const activeTab = tab ?? 'about';
  const review = useAppSelector(state => state.review.reviewInfo);
  const [getDiff, { data: filesDiff }] = useLazyGetDiffsQuery();

  useEffect(() => {
    if (review) {
      getDiff({ projectId: review.projectId, from: review.sourceCommitId, to: review.commitId });
    } else {
      navigate('/homeworks');
    }
  }, [getDiff, navigate, review]);

  useEffect(() => {
    if (!filesDiff) return;

    const filesTree = buildFileTree(filesDiff.diffs);
    const sortedTree = sortTree(filesTree, (itemA, itemB) => {
      if (isFile(itemA) && !isFile(itemB)) {
        return 1;
      }
      if (!isFile(itemA) && isFile(itemB)) {
        return -1;
      }

      return itemA.name.localeCompare(itemB.name);
    });

    dispatch(reviewActions.setFilesTree(sortedTree));
  }, [dispatch, filesDiff]);

  const tabs = useMemo(
    () =>
      ({
        about: {
          title: 'Обзор',
          content: <OverviewTab />,
        },
        files: {
          title: 'Файлы',
          content: <FilesTab />,
        },
      }) satisfies Record<string, { title: string; content: ReactNode }>,
    [],
  );

  return (
    <div className={styles.page}>
      <Header />

      <div className={styles.pageContent}>
        <Tabs
          onSelectTab={tabId => navigate(`/homeworks/${homeworkId}/review/${reviewId}/${role}/${tabId}`)}
          activeTab={activeTab}
          items={Object.entries(tabs).map(([id, { title }]) => ({ id, title }))}
        />

        <div className={styles.tabContent}>{tabs[activeTab].content}</div>
      </div>
    </div>
  );
};
