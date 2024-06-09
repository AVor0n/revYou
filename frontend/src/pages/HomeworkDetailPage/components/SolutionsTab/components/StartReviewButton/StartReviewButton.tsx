import { Button } from '@gravity-ui/uikit';
import { loadSolutionsForReview, startReview, useAppDispatch } from 'app';
import { useAppSelector } from 'app/hooks';

interface StartReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const StartReviewButton = ({ reviewId, homeworkId }: StartReviewButtonProps) => {
  const dispatch = useAppDispatch();
  const requestInProgress = useAppSelector(state => state.review.requestInProgress);

  const onClick = async () => {
    await dispatch(startReview({ reviewId, homeworkId }));
    dispatch(loadSolutionsForReview(homeworkId));
  };

  return (
    <Button view="outlined-action" size="xs" onClick={onClick} loading={requestInProgress.startReview}>
      Начать ревью
    </Button>
  );
};
