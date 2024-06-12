import { Comment } from '@gravity-ui/icons';
import { ArrowToggle, Icon, Tooltip } from '@gravity-ui/uikit';
import { clsx } from 'clsx';
import { useMemo } from 'react';
import { Tree } from '@ui';
import { isFolder, type FileNode, type FolderNode } from 'app';
import { useAppSelector } from 'app/hooks';
import { useActiveFile } from '../../hooks';
import { FileChangesIcon, SkeletonFileTree } from './components';
import styles from './FilesTree.module.scss';

export const FilesTree = () => {
  const { filesTree, threads } = useAppSelector(state => state.review);
  const { activeFile, setActiveFile } = useActiveFile();

  const filesWithComments = useMemo(() => {
    const pathSet = new Set();
    threads?.forEach(thread => {
      const segments = thread.filePath.split('/');
      segments.forEach((_segment, idx) => pathSet.add(segments.slice(0, idx + 1).join('/')));
    });
    return pathSet;
  }, [threads]);

  if (!filesTree) return <SkeletonFileTree />;

  const onClickTreeItem = (treeItem: FolderNode | FileNode) => {
    if (isFolder(treeItem)) return;

    setActiveFile(activeFile?.id === treeItem.id ? null : treeItem);
  };

  return (
    <Tree
      data={filesTree}
      className={styles.FilesTree}
      renderNode={(node, expanded, level, toggleExpand) => (
        <div
          className={clsx(styles.itemWithChildren, activeFile?.id === node.id && styles.active)}
          style={{ paddingInlineStart: level * 10 }}
          onClick={() => (node.children ? toggleExpand(node.id) : onClickTreeItem(node))}
        >
          <div className={styles.itemContainer}>
            {node.children && (
              <ArrowToggle direction={expanded ? 'bottom' : 'right'} size={16} className={styles.icon} />
            )}

            <span className={styles.itemText}>{node.name}</span>

            {filesWithComments.has(node.path) && (
              <Tooltip content="Имеется комментарий">
                <Icon data={Comment} size={14} className={styles.icon} />
              </Tooltip>
            )}

            {!node.children && <FileChangesIcon fileStatus={node.status} />}
          </div>
        </div>
      )}
    />
  );
};
