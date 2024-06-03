import { useCallback, useEffect } from 'react';
import { findInTree } from '@components/Tree';
import { isFile, loadFile, reviewActions, useAppDispatch } from 'app';
import { useAppSelector } from 'app/hooks';
import type { Review } from '@domains';

export interface UseFileDiffContent {
  review: Review;
  filePath: string;
}

export const useFileDiffContent = ({ filePath, review }: UseFileDiffContent) => {
  const dispatch = useAppDispatch();
  const { filesTree, filesCache, requestInProgress } = useAppSelector(state => state.review);

  const sourceFile = filesCache[filePath]?.[review.sourceCommitId];
  const targetFile = filesCache[filePath]?.[review.commitId];

  const loadFileByRef = useCallback(
    (ref: string) => {
      dispatch(loadFile({ filePath, ref }));
      dispatch(reviewActions.addRequestInProgress(`loadFile/${filePath}/${ref}`));
    },
    [dispatch, filePath],
  );

  useEffect(() => {
    if (!filesTree) return;
    const needLoadTarget = targetFile === undefined && !requestInProgress[`loadFile/${filePath}/${review.commitId}`];
    const needLoadSource =
      sourceFile === undefined && !requestInProgress[`loadFile/${filePath}/${review.sourceCommitId}`];

    if (!needLoadSource && !needLoadTarget) return;

    const item = findInTree(filesTree, treeItem => treeItem.path === filePath);
    const fileInfo = item && isFile(item) ? item : null;

    if (!fileInfo) return;

    if (fileInfo.deleted) {
      if (needLoadSource) {
        loadFileByRef(review.sourceCommitId);
      }
      if (needLoadTarget) {
        dispatch(reviewActions.addFileContent({ path: filePath, ref: review.commitId, content: null }));
      }

      return;
    }

    if (fileInfo.newFile) {
      if (needLoadSource) {
        dispatch(reviewActions.addFileContent({ path: filePath, ref: review.sourceCommitId, content: null }));
      }
      if (needLoadTarget) {
        loadFileByRef(review.commitId);
      }

      return;
    }

    loadFileByRef(review.sourceCommitId);
    loadFileByRef(review.commitId);
  }, [dispatch, filePath, filesCache, filesTree, loadFileByRef, requestInProgress, review, sourceFile, targetFile]);

  return {
    sourceFile,
    targetFile,
  };
};
