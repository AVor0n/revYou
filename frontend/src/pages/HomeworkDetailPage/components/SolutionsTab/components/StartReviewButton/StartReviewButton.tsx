import { Button } from '@gravity-ui/uikit';
import { useStartReviewMutation } from '@shared/api';

interface StartReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const StartReviewButton = ({ reviewId, homeworkId }: StartReviewButtonProps) => {
  const [startReview, { isLoading }] = useStartReviewMutation();

  const onClick = () => startReview({ reviewId, homeworkId });

  return (
    <Button view="outlined-action" size="xs" onClick={onClick} loading={isLoading}>
      Начать ревью
    </Button>
  );
};
