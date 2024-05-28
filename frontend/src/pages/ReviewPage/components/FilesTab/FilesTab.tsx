import { Loader, Text } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Panel, PanelGroup, PanelResizeHandle } from 'react-resizable-panels';
import { type CommentsThread } from '@domains';
import {
  getActiveFilePath,
  getFilesTree,
  getSourceActiveFileContent,
  getTargetActiveFileContent,
  loadFileDiff,
  reviewActions,
  useAppDispatch,
  type FolderNode,
  type FileNode,
  getReviewInfo,
  getCommentThreads,
} from 'app';
import { DiffViewer, SkeletonFileTree, FilesTree } from './components';
import './components/DiffViewer/monaco-worker';
import styles from './FilesTab.module.scss';

export const FilesTab = () => {
  const dispatch = useAppDispatch();
  const review = useSelector(getReviewInfo);
  const fileTree = useSelector(getFilesTree);
  const activeFilePath = useSelector(getActiveFilePath);
  const sourceFileContent = useSelector(getSourceActiveFileContent);
  const targetFileContent = useSelector(getTargetActiveFileContent);
  const threads = useSelector(getCommentThreads);
  const [sourceCommitThreads, setSourceCommitThreads] = useState<CommentsThread[]>([]);
  const [targetCommitThreads, setTargetCommitThreads] = useState<CommentsThread[]>([]);

  const hasFilesForComparison = sourceFileContent !== null && targetFileContent !== null;

  const onClickTreeItem = (treeItem: FolderNode | FileNode) => {
    const filePath = activeFilePath === treeItem.path ? '' : treeItem.path;
    dispatch(reviewActions.setActiveFilePath(filePath));

    if (filePath && review?.projectId && review.sourceCommitId && review.commitId) {
      dispatch(
        loadFileDiff({
          path: filePath,
          projectId: review.projectId,
          fromRef: review.sourceCommitId,
          toRef: review.commitId,
        }),
      );
    }
  };

  useEffect(() => {
    if (!review) return;
    const fileThreads = threads?.filter(thread => thread.filePath === activeFilePath) ?? [];
    setSourceCommitThreads(fileThreads.filter(thread => thread.commitSha === review.sourceCommitId));
    setTargetCommitThreads(fileThreads.filter(thread => thread.commitSha === review.commitId));
  }, [activeFilePath, review, threads]);

  return (
    <PanelGroup className={styles.FilesTab} direction="horizontal">
      <Panel defaultSize={10}>
        {fileTree ? (
          <FilesTree data={fileTree} activeFilePath={activeFilePath} onClick={onClickTreeItem} />
        ) : (
          <SkeletonFileTree />
        )}
      </Panel>
      <PanelResizeHandle className={styles.resizer} />
      <Panel defaultSize={90}>
        {hasFilesForComparison && !!review?.reviewId && !!review.commitId && !!review.sourceCommitId && (
          <DiffViewer
            reviewId={review.reviewId}
            sourceContent={sourceFileContent}
            targetContent={targetFileContent}
            sourceSha={review.sourceCommitId}
            targetSha={review.commitId}
            sourceCommentThreads={sourceCommitThreads}
            targetCommentThreads={targetCommitThreads}
          />
        )}

        {!hasFilesForComparison && (
          <div className={styles.placeholderContainer}>
            {activeFilePath ? (
              <Loader size="m" />
            ) : (
              <Text variant="body-2" color="hint">
                Выберите файл для просмотра изменений
              </Text>
            )}
          </div>
        )}
      </Panel>
    </PanelGroup>
  );
};
