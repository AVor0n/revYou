import { ArrowToggle } from '@gravity-ui/uikit';
import { clsx } from 'clsx';
import { Tree } from '@components/Tree';
import { reviewActions, type FileNode, type FolderNode } from 'app';
import { useAppDispatch, useAppSelector } from 'app/hooks';
import { SkeletonFileTree } from './components';
import styles from './FilesTree.module.scss';

export const FilesTree = () => {
  const dispatch = useAppDispatch();
  const { activeFilePath, filesTree } = useAppSelector(state => state.review);

  if (!filesTree) return <SkeletonFileTree />;

  const onClickTreeItem = (treeItem: FolderNode | FileNode) => {
    const filePath = activeFilePath === treeItem.path ? '' : treeItem.path;
    dispatch(reviewActions.setActiveFilePath(filePath));
  };

  return (
    <Tree
      data={filesTree}
      className={styles.FilesTree}
      renderNode={(node, expanded, level, toggleExpand) => (
        <div
          className={clsx(styles.itemWithChildren, activeFilePath === node.path && styles.active)}
          style={{ paddingInlineStart: level * 10 }}
          onClick={() => (node.children ? toggleExpand(node.id) : onClickTreeItem(node))}
        >
          <div className={styles.itemContainer}>
            {node.children && <ArrowToggle direction={expanded ? 'bottom' : 'right'} size={16} />}
            <span className={styles.itemText}>{node.name}</span>
          </div>
        </div>
      )}
    />
  );
};
