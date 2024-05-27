import { ArrowToggle } from '@gravity-ui/uikit';
import { clsx } from 'clsx';
import { Tree } from '@components/Tree';
import { type FilesTree as TFileTree } from 'app';
import styles from './FilesTree.module.scss';

interface FilesTreeProps {
  data: TFileTree;
  activeFilePath?: string;
  onClick: (item: TFileTree[number]) => void;
}

export const FilesTree = ({ data, activeFilePath, onClick }: FilesTreeProps) => (
  <Tree
    data={data}
    className={styles.FilesTree}
    renderNode={(node, expanded, level, toggleExpand) => (
      <div
        className={clsx(styles.itemWithChildren, activeFilePath === node.path && styles.active)}
        style={{ paddingInlineStart: level * 10 }}
        onClick={() => (node.children ? toggleExpand(node.id) : onClick(node))}
      >
        <div className={styles.itemContainer}>
          {node.children && <ArrowToggle direction={expanded ? 'bottom' : 'right'} size={16} />}
          <span className={styles.itemText}>{node.name}</span>
        </div>
      </div>
    )}
  />
);
