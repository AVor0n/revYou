import { Button, TextArea } from '@gravity-ui/uikit';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FormWindow } from '@components/FormWindow';
import { completeReview, loadSolutionsForReview, useAppDispatch } from 'app';
import styles from './CompleteReviewWindow.module.scss';

interface CompleteReviewWindowProps {
  homeworkId: number;
  reviewId: number;
  isOpen: boolean;
  onClose: () => void;
}

export const CompleteReviewWindow = ({ isOpen, onClose, homeworkId, reviewId }: CompleteReviewWindowProps) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [comment, setComment] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const onSubmit = async (status: 'APPROVED' | 'CORRECTIONS_REQUIRED') => {
    setIsLoading(true);
    try {
      await dispatch(
        completeReview({
          homeworkId,
          reviewId,
          comment,
          status,
        }),
      ).unwrap();
      await dispatch(loadSolutionsForReview(homeworkId));
      navigate(`/homeworks/${homeworkId}/for-review`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <FormWindow
      open={isOpen}
      title="Завершение ревью"
      onClose={onClose}
      onSubmit={() => {}}
      footer={
        <div className={styles.footer}>
          <Button view="normal" onClick={onClose} disabled={isLoading} width="max">
            Отмена
          </Button>

          <Button
            view="outlined-warning"
            onClick={() => onSubmit('CORRECTIONS_REQUIRED')}
            disabled={isLoading}
            width="max"
            loading={isLoading}
          >
            Нужны правки
          </Button>

          <Button
            view="outlined-success"
            onClick={() => onSubmit('APPROVED')}
            width="max"
            disabled={isLoading}
            loading={isLoading}
          >
            Утвердить
          </Button>
        </div>
      }
    >
      <TextArea
        view="normal"
        placeholder="Комментарий"
        minRows={10}
        value={comment}
        onChange={event => setComment(event.target.value)}
      />
    </FormWindow>
  );
};
