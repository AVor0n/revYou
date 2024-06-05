import { useMemo } from 'react';
import { type CommentsThread } from '@domains';
import { useAppSelector } from 'app/hooks';

export const useFileDiffComments = () => {
  const { activeFile, threads, reviewInfo } = useAppSelector(state => state.review);

  const [sourceCommentsThreads, targetCommentsThreads, allThreads] = useMemo<CommentsThread[][]>(() => {
    if (!reviewInfo) return [];

    const fileThreads =
      threads?.filter(thread => thread.filePath === activeFile?.oldPath || thread.filePath === activeFile?.path) ?? [];

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
