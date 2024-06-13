import { Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useLazyGetMyReviewsQuery, type Review } from '@api';
import { MySolutionsTable } from './components';
import styles from './MySolutionsTab.module.scss';

export const MySolutionsTab = () => {
  const { homeworkId } = useParams<{ homeworkId: string }>();
  const navigate = useNavigate();
  const [loadMySolutions, { data: mySolutions }] = useLazyGetMyReviewsQuery();

  useEffect(() => {
    if (homeworkId === undefined) {
      navigate('/homeworks');
    } else {
      loadMySolutions({ homeworkId: +homeworkId });
    }
  }, [homeworkId, loadMySolutions, navigate]);

  const onRowClick = (review: Review) => {
    navigate(`/homeworks/${homeworkId}/review/${review.reviewId}/student`);
  };

  return (
    <div className={styles.page}>
      {mySolutions ? (
        <MySolutionsTable data={mySolutions.data} onRowClick={onRowClick} />
      ) : (
        <Skeleton className={styles.skeleton} />
      )}
    </div>
  );
};
