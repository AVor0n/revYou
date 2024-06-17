import { Select } from '@gravity-ui/uikit';
import { useMemo, useState } from 'react';
import { useGetAvailableReviewersQuery, useReplaceReviewerMutation, type Student } from '@api';
import { useApiError } from '@shared/utils';
import { ModalWithQuestion } from '@ui';
import styles from './ReviewerSelect.module.scss';

interface ReviewerSelectProps {
  homeworkId: number;
  reviewId: number;
  reviewer?: Student;
}

export const ReviewerSelect = ({ homeworkId, reviewId, reviewer }: ReviewerSelectProps) => {
  const [reviewerId, setReviewerId] = useState(reviewer?.userId ? [reviewer.userId.toString()] : undefined);
  const [reviewerCandidate, setReviewerCandidate] = useState<Student | null>(null);

  const [changeReviewer, { error: replaceReviewerError }] = useReplaceReviewerMutation();
  useApiError(replaceReviewerError, { name: 'replaceReviewer', title: 'Не удалось изменить ревьюера' });

  const { data: availableReviewers, error: availableReviewersError } = useGetAvailableReviewersQuery({ homeworkId });
  useApiError(availableReviewersError, {
    name: 'loadAvailableReviewers',
    title: 'Не удалось получить список ревьюеров',
  });

  const listOptions = useMemo(
    () => availableReviewers?.data.map(user => ({ value: user.userId.toString(), content: user.username })) ?? [],
    [availableReviewers],
  );

  const onChangeReviewer = (newReviewerId: string[]) => {
    const newReviewer = availableReviewers?.data.find(({ userId }) => userId.toString() === newReviewerId[0]);
    if (!newReviewer) return;
    setReviewerCandidate({ userId: newReviewer.userId, username: newReviewer.username });
  };

  const onCancelChangeReviewer = () => {
    setReviewerCandidate(null);
  };

  const onApproveChangeReviewer = () => {
    if (!reviewerCandidate) return;
    changeReviewer({ homeworkId, reviewerChange: { reviewerId: reviewerCandidate.userId, reviewId } });
    setReviewerId([reviewerCandidate.userId.toString()]);
    setReviewerCandidate(null);
  };

  return (
    <div className={styles.container}>
      Ревьюевер:
      <Select
        value={reviewerId}
        onUpdate={onChangeReviewer}
        disabled={!availableReviewers}
        placeholder="Не выбран"
        options={listOptions}
      />
      <ModalWithQuestion
        open={!!reviewerCandidate}
        question={`Выбрать ${reviewerCandidate?.username} в качестве нового ревьюера?`}
        onYesClick={onApproveChangeReviewer}
        onNoClick={onCancelChangeReviewer}
      />
    </div>
  );
};
