import { useMemo } from 'react';
import { type CommentsThread } from '@domains';
import { useAppSelector } from 'app/hooks';
import { useActiveFile } from '../../../hooks';

export const useFileDiffComments = () => {
  const { activeFile } = useActiveFile();
  const { threads, reviewInfo } = useAppSelector(state => state.review);

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
