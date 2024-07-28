import { Navigate } from 'react-router-dom';
import { useAppSelector } from 'app/hooks';
import { CommentsThreadsList, OverviewCard } from './components';
import { ReviewInfo } from './components/OverviewCard/components';
import styles from './OverviewTab.module.scss';

export const OverviewTab = () => {
  const review = useAppSelector(state => state.review.reviewInfo);
  if (!review) return <Navigate to="/homeworks" />;

  return (
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
};
