import { Button, TextArea } from '@gravity-ui/uikit';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FormWindow } from '@components/FormWindow';
import { completeReview, loadSolutionsForReview, reviewActions, useAppDispatch } from 'app';
import { useAppSelector } from 'app/hooks';
import styles from './CompleteReviewWindow.module.scss';

interface CompleteReviewWindowProps {
  homeworkId: number;
  reviewId: number;
  isOpen: boolean;
  onClose: () => void;
}

export const CompleteReviewWindow = ({ isOpen, homeworkId, reviewId, ...props }: CompleteReviewWindowProps) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [touched, setTouched] = useState(false);
  const [comment, setComment] = useState('');
  const requestInProgress = useAppSelector(state => state.review.requestInProgress);
  const rejectLoading = requestInProgress.rejectSolution;
  const approveLoading = requestInProgress.approveSolution;
  const loading = rejectLoading || approveLoading;

  const onSubmit = async (status: 'APPROVED' | 'CORRECTIONS_REQUIRED') => {
    dispatch(reviewActions.addRequestInProgress(status === 'APPROVED' ? 'approveSolution' : 'rejectSolution'));
    await dispatch(completeReview({ homeworkId, reviewId, comment, status }));
    await dispatch(loadSolutionsForReview(homeworkId));
    navigate(`/homeworks/${homeworkId}/for-review`);
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
          <Button view="normal" onClick={onClose} disabled={loading} width="max">
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
        disabled={loading}
        onBlur={() => setTouched(true)}
        onChange={event => setComment(event.target.value)}
        error={touched && !comment}
        errorMessage="Это поле обязательно для заполнения"
      />
    </FormWindow>
  );
};
