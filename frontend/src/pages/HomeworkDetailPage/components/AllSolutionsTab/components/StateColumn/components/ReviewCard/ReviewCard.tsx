import { Card } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useLazyGetHomeworkQuery, type ReviewInfo } from '@shared/api';
import styles from './ReviewCard.module.scss';

interface ReviewCardProps {
  review: ReviewInfo;
}

export const ReviewCard = ({ review }: ReviewCardProps) => {
  const { homeworkId } = useParams<{ homeworkId: string }>();
  const navigate = useNavigate();
  const [loadHomework, { data: homework }] = useLazyGetHomeworkQuery();

  useEffect(() => {
    if (homeworkId === undefined) {
      navigate('/homeworks');
    } else {
      loadHomework({ homeworkId: +homeworkId });
    }
  }, [homeworkId, loadHomework, navigate]);

  const onClick = () => {
    if (!homework) return;
    navigate(`/homeworks/${homework.id}/review/${review.reviewId}/teacher`);
  };

  return (
    <Card className={styles.card} view="raised" onMouseDown={onClick}>
      <div>Ревью: {review.reviewId}</div>
      <div>Автор: {review.student.username}</div>
      {!!review.reviewer?.username && <div>Ревьювер: {review.reviewer.username}</div>}
    </Card>
  );
};
