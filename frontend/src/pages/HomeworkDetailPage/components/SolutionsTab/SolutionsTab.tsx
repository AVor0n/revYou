import { Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useLazyGetReviewsToDoQuery, type Review } from '@api';
import { reviewActions, useAppDispatch } from '@app';
import { SolutionsTable } from './components';
import styles from './SolutionsTab.module.scss';

export const SolutionTab = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { homeworkId } = useParams<{ homeworkId: string }>();
  const [loadSolutionsForReview, { data: solutionsForReview }] = useLazyGetReviewsToDoQuery();

  useEffect(() => {
    if (!homeworkId) {
      navigate('/homeworks');
    } else {
      loadSolutionsForReview({ homeworkId: +homeworkId });
    }
  }, [homeworkId, loadSolutionsForReview, navigate]);

  const onRowClick = (review: Review) => {
    dispatch(reviewActions.setReviewInfo(review));
    navigate(`/homeworks/${homeworkId}/review/${review.reviewId}/reviewer`);
  };

  return (
    <div className={styles.page}>
      {solutionsForReview ? (
        <SolutionsTable data={solutionsForReview.data} onRowClick={onRowClick} />
      ) : (
        <Skeleton className={styles.skeleton} />
      )}
    </div>
  );
};
