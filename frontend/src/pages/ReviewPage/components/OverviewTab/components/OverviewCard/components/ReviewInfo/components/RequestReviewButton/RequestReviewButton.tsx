import { TriangleExclamation } from '@gravity-ui/icons';
import { Button, Icon, Text } from '@gravity-ui/uikit';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ModalWithQuestion } from '@ui';
import { requestRepeatReview, useAppDispatch } from 'app';
import { useAppSelector } from 'app/hooks';
import styles from './RequestReviewButton.module.scss';

interface RequestReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const RequestReviewButton = ({ reviewId, homeworkId }: RequestReviewButtonProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [isOpenReviewWindow, setIsOpenReviewWindow] = useState(false);
  const requestsInProgress = useAppSelector(state => state.review.requestInProgress);
  const threads = useAppSelector(state => state.review.threads);
  const hasActiveThreads = threads?.some(thread => thread.status === 'ACTIVE');

  const onRequestReview = async () => {
    await dispatch(requestRepeatReview({ homeworkId, reviewId }));
    navigate(`/homeworks/${homeworkId}/my-solution`);
  };

  return (
    <>
      <Button view="action" onClick={() => setIsOpenReviewWindow(true)}>
        Запросить ревью
      </Button>
      <ModalWithQuestion
        open={isOpenReviewWindow}
        question={
          <div className={styles.question}>
            {hasActiveThreads && (
              <div className={styles.warning}>
                <Icon data={TriangleExclamation} stroke="var(--g-color-line-warning)" size={20} />
                <Text color="warning">Имеется активные комментарии</Text>
              </div>
            )}
            <Text>Отправить работу на повторную проверку?</Text>
          </div>
        }
        onYesClick={onRequestReview}
        onNoClick={() => setIsOpenReviewWindow(false)}
        loading={requestsInProgress.requestRepeatReview}
      />
    </>
  );
};
