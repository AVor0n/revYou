import { Select } from '@gravity-ui/uikit';
import { useEffect, useMemo, useState } from 'react';
import { ModalWithQuestion } from '@ui';
import { changeReviewer, loadAvailableReviewers } from 'app';
import { useAppDispatch, useAppSelector } from 'app/hooks';
import type { Student } from '@api';
import styles from './ReviewerSelect.module.scss';

interface ReviewerSelectProps {
  homeworkId: number;
  reviewId: number;
  reviewer?: Student;
}

export const ReviewerSelect = ({ homeworkId, reviewId, reviewer }: ReviewerSelectProps) => {
  const dispatch = useAppDispatch();
  const [reviewerId, setReviewerId] = useState(reviewer?.userId ? [reviewer.userId.toString()] : undefined);
  const [reviewerCandidate, setReviewerCandidate] = useState<Student | null>(null);
  const availableReviewers = useAppSelector(state => state.solution.availableReviewers);
  const listOptions = useMemo(
    () => availableReviewers?.map(user => ({ value: user.userId.toString(), content: user.username })) ?? [],
    [availableReviewers],
  );

  useEffect(() => {
    if (availableReviewers) return;
    dispatch(loadAvailableReviewers(+homeworkId));
  }, [availableReviewers, dispatch, homeworkId]);

  const onChangeReviewer = (newReviewerId: string[]) => {
    const newReviewer = availableReviewers?.find(({ userId }) => userId.toString() === newReviewerId[0]);
    if (!newReviewer) return;
    setReviewerCandidate({ userId: newReviewer.userId, username: newReviewer.username });
  };

  const onCancelChangeReviewer = () => {
    setReviewerCandidate(null);
  };

  const onApproveChangeReviewer = () => {
    if (!reviewerCandidate) return;
    dispatch(changeReviewer({ homeworkId, reviewer: { reviewerId: reviewerCandidate.userId, reviewId } }));
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
