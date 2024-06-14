import { Comment } from '@gravity-ui/icons';
import { ArrowToggle, Icon, Tooltip } from '@gravity-ui/uikit';
import { clsx } from 'clsx';
import { useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLazyGetAllThreadsQuery } from '@shared/api';
import { isFolder, type FilesTreeItem } from '@shared/types';
import { Tree } from '@ui';
import { useAppSelector } from 'app/hooks';
import { useActiveFile } from '../../hooks';
import { FileChangesIcon, SkeletonFileTree } from './components';
import styles from './FilesTree.module.scss';

export const FilesTree = () => {
  const navigate = useNavigate();
  const { filesTree, reviewInfo } = useAppSelector(state => state.review);
  const { activeFile, setActiveFile } = useActiveFile();
  const [loadThreads, { data: threads }] = useLazyGetAllThreadsQuery();

  useEffect(() => {
    if (reviewInfo?.reviewId === undefined) {
      navigate('/homeworks');
    } else {
      loadThreads({ reviewId: reviewInfo.reviewId });
    }
  }, [loadThreads, navigate, reviewInfo?.reviewId]);

  const filesWithComments = useMemo(() => {
    const pathSet = new Set();
    threads?.data.forEach(thread => {
      const segments = thread.filePath.split('/');
      segments.forEach((_segment, idx) => pathSet.add(segments.slice(0, idx + 1).join('/')));
    });
    return pathSet;
  }, [threads]);

  if (!filesTree) return <SkeletonFileTree />;

  const onClickTreeItem = (treeItem: FilesTreeItem) => {
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
