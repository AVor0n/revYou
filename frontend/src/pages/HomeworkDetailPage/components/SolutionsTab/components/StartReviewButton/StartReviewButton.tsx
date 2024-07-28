import { Button } from '@gravity-ui/uikit';
import { useStartReviewMutation } from '@shared/api';
import { useApiError } from '@shared/utils';

interface StartReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const StartReviewButton = ({ reviewId, homeworkId }: StartReviewButtonProps) => {
  const [startReview, { isLoading, error }] = useStartReviewMutation();

  useApiError(error, { name: 'startReview', title: 'Не начать ревью' });

  const onClick = () => startReview({ reviewId, homeworkId });

  return (
    <Button view="outlined-action" size="xs" onClick={onClick} loading={isLoading}>
      Начать ревью
    </Button>
  );
};
