import { TextArea } from '@gravity-ui/uikit';
import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { type FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FormWindow } from '@components/FormWindow';
import { requestRepeatReview, useAppDispatch } from 'app';
import type { AxiosError } from 'axios';

interface RequestReviewWindowProps {
  homeworkId: number;
  reviewId: number;
  isOpen: boolean;
  onClose: () => void;
}

export const RequestReviewWindow = ({ isOpen, onClose, homeworkId, reviewId }: RequestReviewWindowProps) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [comment, setComment] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const onSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setIsLoading(true);
    try {
      await dispatch(requestRepeatReview({ homeworkId, reviewId })).unwrap();
      navigate(`/homeworks/${homeworkId}/my-solution`);
    } catch (e) {
      const error = (e as AxiosError).response?.data as { message: string };
      toaster.add({
        name: 'requestNewReview',
        title: 'Ошибка отправки',
        content: error.message,
        theme: 'danger',
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <FormWindow
      open={isOpen}
      title="Запрос нового ревью"
      onClose={onClose}
      onSubmit={onSubmit}
      saveText="Отправить"
      saveLoading={isLoading}
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
