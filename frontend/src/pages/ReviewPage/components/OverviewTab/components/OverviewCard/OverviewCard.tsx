import { Card, Text } from '@gravity-ui/uikit';
import { useParams } from 'react-router-dom';
import { ReviewStatus } from '@components/ReviewStatus';
import { CompleteReviewButton, RequestReviewButton } from './components';
import type { Review } from '@domains';
import styles from './OverviewCard.module.scss';

interface OverviewCardProps {
  review: Review;
}

export const OverviewCard = ({ review }: OverviewCardProps) => {
  const { homeworkId, reviewId, role } = useParams<{
    homeworkId: string;
    reviewId: string;
    role: 'student' | 'reviewer';
  }>();

  const resolution = review.reviewAttempts?.at(-1)?.resolution;

  if (!homeworkId || !reviewId) return null;

  const showRequestReviewButton = role === 'student' && review.status === 'CORRECTIONS_REQUIRED';
  const showCompleteReviewButton =
    role === 'reviewer' && (review.status === 'REVIEW_STARTED' || review.status === 'CORRECTIONS_LOADED');

  return (
    <Card view="raised" className={styles.card}>
      <div className={styles.status}>
        <ReviewStatus status={review.status} />
      </div>

      {resolution ? (
        <Text variant="body-2">{resolution}</Text>
      ) : (
        <Text variant="body-2" className={styles.commentPlaceholder}>
          Комментария нет
        </Text>
      )}

      <div className={styles.actionsContainer}>
        {showRequestReviewButton && <RequestReviewButton homeworkId={+homeworkId} reviewId={+reviewId} />}
        {showCompleteReviewButton && <CompleteReviewButton homeworkId={+homeworkId} reviewId={+reviewId} />}
      </div>
    </Card>
  );
};
