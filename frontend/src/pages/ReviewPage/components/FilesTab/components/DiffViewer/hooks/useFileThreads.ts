import { useEffect, useMemo } from 'react';
import { useLazyGetAllThreadsQuery, type CommentsThread } from '@api';
import { useAppSelector } from 'app/hooks';
import { useActiveFile } from '../../../hooks';

export const useFileDiffComments = () => {
  const { activeFile } = useActiveFile();
  const { reviewInfo } = useAppSelector(state => state.review);
  const [loadThreads, { data: threads }] = useLazyGetAllThreadsQuery();

  useEffect(() => {
    if (reviewInfo?.reviewId === undefined) throw new Error('not provided reviewId');
    loadThreads({ reviewId: reviewInfo.reviewId });
  }, [loadThreads, reviewInfo]);

  const [sourceCommentsThreads, targetCommentsThreads, allThreads] = useMemo<CommentsThread[][]>(() => {
    if (!reviewInfo) return [];

    const fileThreads =
      threads?.data.filter(thread => thread.filePath === activeFile?.oldPath || thread.filePath === activeFile?.path) ??
      [];

    return [
      fileThreads.filter(thread => thread.commitSha === reviewInfo.sourceCommitId),
      fileThreads.filter(thread => thread.commitSha !== reviewInfo.sourceCommitId),
      fileThreads,
    ];
  }, [activeFile, reviewInfo, threads]);

  return {
    sourceCommentsThreads,
    targetCommentsThreads,
    allThreads,
  };
};
