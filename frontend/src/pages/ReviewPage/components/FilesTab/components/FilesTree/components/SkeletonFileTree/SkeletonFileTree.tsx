import { Skeleton } from '@gravity-ui/uikit';
import { Tree, getAllIds } from '@ui';
import { skeletonTreeData } from './constants';
import styles from './SkeletonFileTree.module.scss';

export const SkeletonFileTree = () => (
  <div className={styles.SkeletonFileTree}>
    <Tree
      className={styles.tree}
      data={skeletonTreeData}
      expandedNodes={getAllIds(skeletonTreeData)}
      renderNode={(_node, _expanded, level) => (
        <div style={{ marginLeft: level * 16 }}>
          <Skeleton className={styles.skeleton} />
        </div>
      )}
    />
  </div>
);
