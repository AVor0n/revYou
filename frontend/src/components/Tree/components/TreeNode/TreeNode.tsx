import type { TreeNode } from '../../types';
import type { ReactNode } from 'react';

export interface TreeItemProps<T> {
  level: number;
  data: T;
  expandedNodes: Set<string>;
  toggleExpand: (nodeId: string) => void;
  renderNode: (node: T, expanded: boolean, level: number, toggleExpand: (nodeId: string) => void) => ReactNode;
}

export const TreeItem = <T extends TreeNode>({
  data,
  level,
  expandedNodes,
  renderNode,
  toggleExpand,
}: TreeItemProps<T>) => {
  const expanded = expandedNodes.has(data.id);
  return (
    <div>
      {renderNode(data, expanded, level, toggleExpand)}
      {expanded &&
        data.children?.map((item: T) => (
          <TreeItem
            key={item.id}
            level={level + 1}
            data={item}
            renderNode={renderNode}
            expandedNodes={expandedNodes}
            toggleExpand={toggleExpand}
          />
        ))}
    </div>
  );
};
