import { useMemo } from 'react';
import { Navigate, useParams } from 'react-router-dom';
import { ReviewStatus } from '@ui';
import { CompleteReviewButton, RequestReviewButton } from './components';
import { ReviewerSelect } from './components/ReviewerSelect/ReviewerSelect';
import type { Student, ReviewStatus as TReviewStatus } from '@api';
import styles from './ReviewInfo.module.scss';

interface ReviewInfoProps {
  status: TReviewStatus;
  author?: Student;
  reviewer?: Student;
}

export const ReviewInfo = ({ status, author, reviewer }: ReviewInfoProps) => {
  const { homeworkId, reviewId, role } = useParams<{
    homeworkId: string;
    reviewId: string;
    role: 'student' | 'reviewer' | 'teacher';
  }>();

  const permissions = useMemo(
    () => ({
      showAuthor: role === 'teacher',
      changeReviewer: role === 'teacher',
      requestReview: role === 'student' && status === 'CORRECTIONS_REQUIRED',
      completeReview:
        (role === 'teacher' && status !== 'APPROVED' && status !== 'ARCHIVED') ||
        (role === 'reviewer' && (status === 'REVIEW_STARTED' || status === 'CORRECTIONS_LOADED')),
    }),
    [role, status],
  );

  if (!homeworkId) return <Navigate to="/homeworks" />;
  if (!reviewId) return <Navigate to={`/homeworks/${homeworkId}/review`} />;

  return (
    <div className={styles.container}>
      <div className={styles.column}>
        Статус: <ReviewStatus status={status} />
      </div>

      {permissions.showAuthor && <div className={styles.column}>Автор: {author?.username}</div>}

      {permissions.changeReviewer && (
        <div className={styles.column}>
          <ReviewerSelect reviewer={reviewer} homeworkId={+homeworkId} reviewId={+reviewId} />
        </div>
      )}

      {permissions.completeReview && (
        <div className={styles.column}>
          <CompleteReviewButton reviewId={+reviewId} homeworkId={+homeworkId} />
        </div>
      )}

      {permissions.requestReview && (
        <div className={styles.column}>
          <RequestReviewButton reviewId={+reviewId} homeworkId={+homeworkId} />
        </div>
      )}
    </div>
  );
};
