import { useMemo } from 'react';
import { type CommentsThread } from '@domains';
import { useAppSelector } from 'app/hooks';

export const useFileDiffComments = () => {
  const { activeFilePath, threads, reviewInfo } = useAppSelector(state => state.review);

  const [sourceCommentsThreads, targetCommentsThreads, allThreads] = useMemo<CommentsThread[][]>(() => {
    if (!reviewInfo) return [];

    const fileThreads = threads?.filter(thread => thread.filePath === activeFilePath) ?? [];
    return [
      fileThreads.filter(thread => thread.commitSha === reviewInfo.sourceCommitId),
      fileThreads.filter(thread => thread.commitSha !== reviewInfo.sourceCommitId),
      fileThreads,
    ];
  }, [activeFilePath, reviewInfo, threads]);

  return {
    sourceCommentsThreads,
    targetCommentsThreads,
    allThreads,
  };
};
