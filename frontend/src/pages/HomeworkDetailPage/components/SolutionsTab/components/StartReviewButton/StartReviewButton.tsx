import { Button } from '@gravity-ui/uikit';
import { loadSolutionsForReview, startReview, useAppDispatch } from 'app';

interface StartReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const StartReviewButton = ({ reviewId, homeworkId }: StartReviewButtonProps) => {
  const dispatch = useAppDispatch();

  const onClick = async () => {
    await dispatch(startReview({ reviewId, homeworkId })).unwrap();
    dispatch(loadSolutionsForReview(homeworkId));
  };

  return (
    <Button view="outlined-action" size="xs" onClick={onClick}>
      Начать ревью
    </Button>
  );
};
