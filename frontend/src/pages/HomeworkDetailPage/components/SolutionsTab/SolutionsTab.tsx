import { Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { type Homework } from '@domains';
import { getSolutions, loadSolutions, useAppDispatch } from 'app';
import { SolutionsTable } from './components';
import styles from './SolutionsTab.module.scss';

interface SolutionTabProps {
  homeworkInfo: Homework | null;
}

export const SolutionTab = ({ homeworkInfo }: SolutionTabProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const solutions = useSelector(getSolutions);

  useEffect(() => {
    if (!homeworkInfo?.id) return;
    dispatch(loadSolutions(homeworkInfo.id));
  }, [dispatch, homeworkInfo]);

  return (
    <div className={styles.page}>
      {solutions ? (
        <SolutionsTable data={solutions} onRowClick={({ projectId }) => navigate(`/solutions/${projectId}`)} />
      ) : (
        <Skeleton className={styles.skeleton} />
      )}
    </div>
  );
};
