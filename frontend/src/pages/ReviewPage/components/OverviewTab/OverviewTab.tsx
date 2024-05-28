import { Card, Text } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { ReviewStatus } from '@components/ReviewStatus';
import { getCommentThreads } from 'app';
import { CommentsThread } from '../CommentsThread';
import { CompleteReviewButton, RequestReviewButton } from './components';
import type { Review } from '@domains';
import styles from './OverviewTab.module.scss';

interface OverviewTabProps {
  review: Review;
}

export const OverviewTab = ({ review }: OverviewTabProps) => {
  const { homeworkId, reviewId, role } = useParams<{
    homeworkId: string;
    reviewId: string;
    role: 'student' | 'reviewer';
  }>();
  const commentThreads = useSelector(getCommentThreads);

  const resolution = review.reviewAttempts?.at(-1)?.resolution;

  if (!homeworkId || !reviewId) return null;

  const showRequestReviewButton = role === 'student' && review.status === 'CORRECTIONS_REQUIRED';
  const showCompleteReviewButton =
    role === 'reviewer' && (review.status === 'REVIEW_STARTED' || review.status === 'CORRECTIONS_LOADED');

  return (
    <div className={styles.OverviewTab}>
      <Card view="raised" className={styles.card}>
        <div className={styles.status}>
          <ReviewStatus status={review.status ?? ''} />
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

      {commentThreads?.length !== 0 && (
        <div className={styles.threadsContainer}>
          {commentThreads?.toReversed().map(thread => <CommentsThread thread={thread} key={thread.threadId} />)}
        </div>
      )}
    </div>
  );
};
