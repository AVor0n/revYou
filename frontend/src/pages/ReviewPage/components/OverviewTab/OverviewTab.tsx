import { CommentsThreadsList, OverviewCard } from './components';
import { ReviewInfo } from './components/OverviewCard/components';
import type { Review } from '@api';
import type { FullReviewInfo } from 'app';
import styles from './OverviewTab.module.scss';

interface OverviewTabProps {
  review: Review | FullReviewInfo;
}

export const OverviewTab = ({ review }: OverviewTabProps) => (
  <div className={styles.OverviewTab}>
    <ReviewInfo
      status={review.status}
      author={'student' in review ? review.student : undefined}
      reviewer={'reviewer' in review ? review.reviewer : undefined}
    />
    <OverviewCard review={review} />
    <CommentsThreadsList review={review} />
  </div>
);
