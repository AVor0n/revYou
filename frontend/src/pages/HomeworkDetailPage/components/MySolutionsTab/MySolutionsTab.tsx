import { Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { type Homework, type Review } from '@domains';
import { getMySolutions, loadMySolutions, reviewActions, useAppDispatch } from 'app';
import { MySolutionsTable } from './components';
import styles from './MySolutionsTab.module.scss';

interface MySolutionsTabProps {
  homeworkInfo: Homework | null;
}

export const MySolutionsTab = ({ homeworkInfo }: MySolutionsTabProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const mySolutions = useSelector(getMySolutions);

  useEffect(() => {
    if (!homeworkInfo?.id) throw new Error('homework info is null');
    dispatch(loadMySolutions(homeworkInfo.id));
  }, [dispatch, homeworkInfo]);

  const onRowClick = (review: Review) => {
    dispatch(reviewActions.setReviewInfo(review));
    navigate(`/homeworks/${homeworkInfo?.id}/review/${review.reviewId}/student`);
  };

  return (
    <div className={styles.page}>
      {mySolutions ? (
        <MySolutionsTable data={mySolutions} onRowClick={onRowClick} />
      ) : (
        <Skeleton className={styles.skeleton} />
      )}
    </div>
  );
};
