import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getCommentThreads, loadThreads } from 'app';
import { CommentsThreadWithDiff } from './components';
import type { Review } from '@domains';
import styles from './CommentsThreadsList.module.scss';

interface CommentsThreadsListProps {
  review: Review;
}

export const CommentsThreadsList = ({ review }: CommentsThreadsListProps) => {
  const dispatch = useDispatch();
  const commentThreads = useSelector(getCommentThreads);

  useEffect(() => {
    dispatch(loadThreads(review.reviewId));
  }, [dispatch, review]);

  return (
    <div className={styles.container}>
      {commentThreads?.map(thread => (
        <CommentsThreadWithDiff
          thread={thread}
          key={thread.threadId}
          sourceSha={review.sourceCommitId}
          targetSha={review.commitId}
        />
      ))}
    </div>
  );
};
