import { useEffect, useMemo } from 'react';
import { useDispatch } from 'react-redux';
import { findInTree } from '@components/Tree';
import { isFile, loadThreads, type FileNode } from 'app';
import { useAppSelector } from 'app/hooks';
import { CommentsThreadWithDiff } from './components';
import type { CommentsThread, Review } from '@domains';
import styles from './CommentsThreadsList.module.scss';

interface CommentsThreadsListProps {
  review: Review;
}

export const CommentsThreadsList = ({ review }: CommentsThreadsListProps) => {
  const dispatch = useDispatch();
  const { threads, filesTree } = useAppSelector(state => state.review);

  const threadsWithFileInfo = useMemo(() => {
    if (!threads || !filesTree) return [];

    return threads.reduce((acc: { thread: CommentsThread; fileInfo: FileNode }[], thread) => {
      const treeItem = findInTree(filesTree, item => {
        if (!isFile(item)) return false;
        return thread.filePath === item.oldPath || thread.filePath === item.path;
      });

      if (treeItem && isFile(treeItem)) {
        acc.push({ thread, fileInfo: treeItem });
      }
      return acc;
    }, []);
  }, [filesTree, threads]);

  useEffect(() => {
    dispatch(loadThreads(review.reviewId));
  }, [dispatch, review]);

  return (
    <div className={styles.container}>
      {threadsWithFileInfo.map(({ thread, fileInfo }) => (
        <CommentsThreadWithDiff
          thread={thread}
          key={thread.threadId}
          file={fileInfo}
          sourceSha={review.sourceCommitId}
          targetSha={review.commitId}
        />
      ))}
    </div>
  );
};
