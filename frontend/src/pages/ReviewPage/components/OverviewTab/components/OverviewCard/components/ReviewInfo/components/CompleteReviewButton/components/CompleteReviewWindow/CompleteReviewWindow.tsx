import { Button, TextArea } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAddReviewResolutionMutation } from '@shared/api';
import { FormWindow } from '@ui';
import styles from './CompleteReviewWindow.module.scss';

interface CompleteReviewWindowProps {
  homeworkId: number;
  reviewId: number;
  isOpen: boolean;
  onClose: () => void;
}

export const CompleteReviewWindow = ({ isOpen, homeworkId, reviewId, ...props }: CompleteReviewWindowProps) => {
  const navigate = useNavigate();
  const [resolution, setResolution] = useState<'APPROVED' | 'CORRECTIONS_REQUIRED' | null>(null);
  const [completeReview, { isLoading, isSuccess }] = useAddReviewResolutionMutation();
  const [touched, setTouched] = useState(false);
  const [comment, setComment] = useState('');
  const approveLoading = isLoading && resolution === 'APPROVED';
  const rejectLoading = isLoading && resolution === 'CORRECTIONS_REQUIRED';

  useEffect(() => {
    if (isSuccess) navigate(`/homeworks/${homeworkId}/for-review`);
  }, [homeworkId, isSuccess, navigate]);

  useEffect(() => {
    if (!isLoading) setResolution(null);
  }, [isLoading]);

  const onSubmit = (status: 'APPROVED' | 'CORRECTIONS_REQUIRED') => {
    completeReview({ homeworkId, reviewId, reviewResolution: { status, resolution: comment } });
    setResolution(status);
  };

  const onClose = () => {
    setTouched(false);
    props.onClose();
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
            width="max"
            disabled={!comment || approveLoading}
            loading={rejectLoading}
          >
            Нужны правки
          </Button>

          <Button
            view="outlined-success"
            onClick={() => onSubmit('APPROVED')}
            width="max"
            disabled={!comment || rejectLoading}
            loading={approveLoading}
          >
            Утвердить
          </Button>
        </div>
      }
    >
      <TextArea
        view="normal"
        placeholder="Оставьте комментарий..."
        minRows={10}
        value={comment}
        disabled={isLoading}
        onBlur={() => setTouched(true)}
        onChange={event => setComment(event.target.value)}
        error={touched && !comment}
        errorMessage="Это поле обязательно для заполнения"
      />
    </FormWindow>
  );
};
