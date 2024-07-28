import { Card, Text } from '@gravity-ui/uikit';
import { useParams } from 'react-router-dom';
import type { Review } from '@api';
import type { FullReviewInfo } from '@shared/types';
import styles from './OverviewCard.module.scss';

interface OverviewCardProps {
  review: FullReviewInfo | Review;
}

export const OverviewCard = ({ review }: OverviewCardProps) => {
  const { homeworkId, reviewId } = useParams<{ homeworkId: string; reviewId: string }>();

  const resolution = review.reviewAttempts?.at(-1)?.resolution;

  if (!homeworkId || !reviewId || !resolution) return null;

  return (
    <Card view="raised" className={styles.card}>
      <Text variant="body-2">{resolution}</Text>
    </Card>
  );
};
