import type { FileNode, FolderNode, FilesTree, FileStatus } from '../types';
import type { Diff } from '@api';

const getFileStatusFromDiff = (file: Diff): FileStatus => {
  if (file.deleted_file) return 'deleted';
  if (file.new_file) return 'added';
  if (file.renamed_file) return 'renamed';
  return 'changed';
};

export function buildFileTree(changedFiles: Diff[]) {
  const changedFilesMap = new Map(changedFiles.map(file => [`/${file.new_path}`, file]));

  const pathToNodeMap = new Map<string, FileNode | FolderNode>(
    changedFiles.flatMap(({ new_path }) =>
      new_path.split('/').map((_, idx, partsOfPath) => {
        const partialPath = `/${partsOfPath.slice(0, idx + 1).join('/')}`;
        const isFolder = idx !== partsOfPath.length - 1;

        if (isFolder) {
          const folderNode: FolderNode = {
            id: partialPath,
            name: partsOfPath.at(idx) ?? '',
            path: partialPath,
            children: [],
          };
          return [partialPath, folderNode];
        }

        const changeFileInfo = changedFilesMap.get(partialPath);
        const fileNode: FileNode = {
          id: partialPath,
          name: partsOfPath.at(idx) ?? '',
          path: partialPath,
          status: changeFileInfo ? getFileStatusFromDiff(changeFileInfo) : 'unchanged',
          oldPath: `/${changeFileInfo?.old_path ?? changeFileInfo?.new_path}`,
        };

        return [partialPath, fileNode];
      }),
    ),
  );

  const tree: FilesTree = [];

  for (const node of pathToNodeMap.values()) {
    const { path } = node;
    const parentNodePath = path.split('/').slice(0, -1).join('/');
    const parentNode = pathToNodeMap.get(parentNodePath);

    if (parentNode?.children) {
      parentNode.children.push(node);
    } else {
      tree.push(node);
    }
  }

  return tree;
}
