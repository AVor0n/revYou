import { Card } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getSelectedHomework, reviewActions, useAppDispatch, type FullReviewInfo } from 'app';
import styles from './ReviewCard.module.scss';

interface ReviewCardProps {
  review: FullReviewInfo;
}

export const ReviewCard = ({ review }: ReviewCardProps) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const homework = useSelector(getSelectedHomework);

  const onClick = () => {
    if (!homework) return;
    dispatch(reviewActions.setReviewInfo(review));
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
