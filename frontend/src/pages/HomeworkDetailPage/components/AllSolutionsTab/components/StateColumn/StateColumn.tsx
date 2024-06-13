import { ReviewStatus } from '@ui';
import { ReviewCard } from './components';
import type { ReviewInfo, ReviewStatus as TReviewStatus } from '@api';
import styles from './StateColumn.module.scss';

interface StateColumnProps {
  status: TReviewStatus;
  reviewList: ReviewInfo[];
}

export const StateColumn = ({ status, reviewList }: StateColumnProps) => (
  <div className={styles.container}>
    <div className={styles.header}>
      <ReviewStatus status={status} />
    </div>

    <div className={styles.content}>
      {reviewList.map(review => (
        <ReviewCard review={review} key={review.reviewId} />
      ))}
    </div>
  </div>
);
