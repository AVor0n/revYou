export interface ReviewSchema {
  solutionInfo: SolutionInfo | null;
  filesTree: FilesTree | null;
  activeFilePath: string;
  sourceActiveFileContent: string | null;
  targetActiveFileContent: string | null;
  error: string;
}

export interface SolutionInfo {
  projectId: string;
  authorId: string;
  sourceCommitHash: string;
  targetCommitHash: string;
}

export type FilesTree = (FolderNode | FileNode)[];

export interface FolderNode {
  id: string;
  name: string;
  path: string;
  children: FilesTree;
}

export interface FileNode {
  id: string;
  name: string;
  path: string;
  newFile: boolean;
  renamed: boolean;
  deleted: boolean;
  children?: never;
}

export interface FileTreeDiff {
  diffs: FileDiff[];
}

export interface FileDiff {
  new_path: string;
  old_path: string;
  new_file: boolean;
  renamed_file: boolean;
  deleted_file: boolean;
  generated_file: null;
}
