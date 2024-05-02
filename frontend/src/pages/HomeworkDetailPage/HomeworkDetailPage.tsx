import { Skeleton, Tabs } from '@gravity-ui/uikit';
import { useEffect, useMemo } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { getSelectedHomework, getSolutionInfo, selectHomework, useAppDispatch } from 'app';
import { loadSolution } from 'app/entities/Review/services';
import { DescriptionTab, FilesTab, Header } from './components';
import styles from './HomeworkDetailPage.module.scss';

export const HomeworkDetailPage = () => {
  const { id: homeworkId, tab } = useParams<{ id: string; tab: keyof typeof tabs }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const homeworkInfo = useSelector(getSelectedHomework);
  const solutionInfo = useSelector(getSolutionInfo);
  const activeTab = tab ?? 'about';

  useEffect(() => {
    if (homeworkId !== undefined) {
      dispatch(selectHomework(+homeworkId));
      dispatch(loadSolution(+homeworkId));
    }
  }, [dispatch, homeworkId]);

  const tabs = useMemo(
    () => ({
      about: {
        title: 'Описание',
        content: <DescriptionTab homeworkInfo={homeworkInfo} />,
      },
      ...(solutionInfo
        ? {
            solution: {
              title: 'Мое решение',
              content: <div>Solution</div>,
            },
            files: {
              title: 'Файлы',
              content: <FilesTab />,
            },
          }
        : {}),
      solutions: {
        title: 'Решения',
        content: <div>Solutions</div>,
      },
    }),
    [homeworkInfo, solutionInfo],
  );

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
          {homeworkInfo ? tabs[activeTab]?.content : <Skeleton className={styles.contentSkeleton} />}
        </div>
      </div>
    </div>
  );
};
