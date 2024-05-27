import { Button } from '@gravity-ui/uikit';
import { useState } from 'react';
import { CompleteReviewWindow } from './components';

interface CompleteReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const CompleteReviewButton = ({ reviewId, homeworkId }: CompleteReviewButtonProps) => {
  const [isOpenReviewWindow, setIsOpenReviewWindow] = useState(false);

  return (
    <>
      <Button view="action" onClick={() => setIsOpenReviewWindow(true)}>
        Завершить ревью
      </Button>

      <CompleteReviewWindow
        isOpen={isOpenReviewWindow}
        onClose={() => setIsOpenReviewWindow(false)}
        reviewId={reviewId}
        homeworkId={homeworkId}
      />
    </>
  );
};
