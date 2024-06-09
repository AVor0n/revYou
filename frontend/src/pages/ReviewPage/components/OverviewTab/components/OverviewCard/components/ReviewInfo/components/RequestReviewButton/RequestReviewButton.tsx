import { Button } from '@gravity-ui/uikit';
import { useState } from 'react';
import { RequestReviewWindow } from './components';

interface RequestReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const RequestReviewButton = ({ reviewId, homeworkId }: RequestReviewButtonProps) => {
  const [isOpenReviewWindow, setIsOpenReviewWindow] = useState(false);

  return (
    <>
      <Button view="action" onClick={() => setIsOpenReviewWindow(true)}>
        Запросить ревью
      </Button>

      <RequestReviewWindow
        isOpen={isOpenReviewWindow}
        onClose={() => setIsOpenReviewWindow(false)}
        reviewId={reviewId}
        homeworkId={homeworkId}
      />
    </>
  );
};
