import type { FileNode, FolderNode, FilesTree } from '../types';
import type { Diff } from '@domains';

export function buildFileTree(changedFiles: Diff[]) {
  const changedFilesMap = new Map(changedFiles.map(file => [file.new_path, file]));

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
          newFile: !!changeFileInfo?.new_file,
          renamed: !!changeFileInfo?.renamed_file,
          deleted: !!changeFileInfo?.deleted_file,
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
