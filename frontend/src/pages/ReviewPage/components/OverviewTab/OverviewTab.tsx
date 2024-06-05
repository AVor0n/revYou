import { CommentsThreadsList, OverviewCard } from './components';
import type { Review } from '@domains';
import styles from './OverviewTab.module.scss';

interface OverviewTabProps {
  review: Review;
}

export const OverviewTab = ({ review }: OverviewTabProps) => (
  <div className={styles.OverviewTab}>
    <OverviewCard review={review} />
    <CommentsThreadsList review={review} />
  </div>
);
