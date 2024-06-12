import { Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { type Homework, type Review } from '@api';
import { getSolutionsForReview, loadSolutionsForReview, reviewActions, useAppDispatch } from 'app';
import { SolutionsTable } from './components';
import styles from './SolutionsTab.module.scss';

interface SolutionTabProps {
  homeworkInfo: Homework | null;
}

export const SolutionTab = ({ homeworkInfo }: SolutionTabProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const solutionsForReview = useSelector(getSolutionsForReview);

  useEffect(() => {
    if (!homeworkInfo?.id) return;
    dispatch(loadSolutionsForReview(homeworkInfo.id));
  }, [dispatch, homeworkInfo]);

  const onRowClick = (review: Review) => {
    dispatch(reviewActions.setReviewInfo(review));
    navigate(`/homeworks/${homeworkInfo?.id}/review/${review.reviewId}/reviewer`);
  };

  return (
    <div className={styles.page}>
      {solutionsForReview ? (
        <SolutionsTable data={solutionsForReview} onRowClick={onRowClick} />
      ) : (
        <Skeleton className={styles.skeleton} />
      )}
    </div>
  );
};
