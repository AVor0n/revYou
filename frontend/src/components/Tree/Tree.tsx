import { useState, type ReactNode } from 'react';
import { TreeItem } from './components';
import type { TreeNode } from './types';

interface TreeProps<T> {
  className?: string;
  data: T[];
  expandedNodes?: string[];
  renderNode: (node: T, expanded: boolean, level: number, toggleExpand: (nodeId: string) => void) => ReactNode;
}

export const Tree = <T extends TreeNode>({ className, data, renderNode, ...props }: TreeProps<T>) => {
  const [expandedNodes, setExpandedNodes] = useState(new Set<string>(props.expandedNodes ?? []));

  const toggleExpandNode = (nodeId: string) => {
    if (expandedNodes.has(nodeId)) {
      expandedNodes.delete(nodeId);
    } else {
      expandedNodes.add(nodeId);
    }
    setExpandedNodes(new Set(expandedNodes));
  };

  return (
    <div className={className}>
      {data.map(node => (
        <TreeItem
          level={0}
          data={node}
          key={node.id}
          expandedNodes={expandedNodes}
          renderNode={renderNode}
          toggleExpand={toggleExpandNode}
        />
      ))}
    </div>
  );
};
