import type { CommentsThread, Review } from '@domains';

export interface ReviewSchema {
  reviewInfo: Review | null;
  filesTree: FilesTree | null;
  activeFilePath: string;
  sourceActiveFileContent: string | null;
  targetActiveFileContent: string | null;
  threads: CommentsThread[] | null;
  createThreadInProgress: boolean;
  error: string;
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
