import { Loader, Text } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { Panel, PanelGroup, PanelResizeHandle } from 'react-resizable-panels';
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
} from 'app';
import { DiffViewer, SkeletonFileTree, FilesTree } from './components';
import styles from './FilesTab.module.scss';
import './monaco-worker';

export const FilesTab = () => {
  const dispatch = useAppDispatch();
  const review = useSelector(getReviewInfo);
  const fileTree = useSelector(getFilesTree);
  const activeFilePath = useSelector(getActiveFilePath);
  const sourceFileContent = useSelector(getSourceActiveFileContent);
  const targetFileContent = useSelector(getTargetActiveFileContent);

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
        {hasFilesForComparison && <DiffViewer sourceContent={sourceFileContent} targetContent={targetFileContent} />}

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
