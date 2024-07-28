import { TriangleExclamation } from '@gravity-ui/icons';
import { Button, Icon, Text } from '@gravity-ui/uikit';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useGetAllThreadsQuery, useUploadCorrectionsMutation } from '@shared/api';
import { useApiError } from '@shared/utils';
import { ModalWithQuestion } from '@ui';
import styles from './RequestReviewButton.module.scss';

interface RequestReviewButtonProps {
  reviewId: number;
  homeworkId: number;
}

export const RequestReviewButton = ({ reviewId, homeworkId }: RequestReviewButtonProps) => {
  const navigate = useNavigate();
  const [isOpenReviewWindow, setIsOpenReviewWindow] = useState(false);

  const { data: threads, error: threadsError } = useGetAllThreadsQuery({ reviewId });
  useApiError(threadsError, { name: 'loadThreads', title: 'Не удалось загрузить список комментариев' });

  const [requestRepeatReview, { isLoading, error: uploadCorrectionsError }] = useUploadCorrectionsMutation();
  useApiError(uploadCorrectionsError, { name: 'uploadCorrections', title: 'Ошибка при запросе ревью' });

  const hasActiveThreads = threads?.data.some(thread => thread.status === 'ACTIVE');

  const onRequestReview = () => {
    requestRepeatReview({ homeworkId, reviewId });
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
        loading={isLoading}
      />
    </>
  );
};
