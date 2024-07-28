import type { ReviewInfo } from '@shared/api';

export type FullReviewInfo = ReviewInfo & { sourceCommitId: string };

export type FilesTree = FilesTreeItem[];

export type FilesTreeItem = FolderNode | FileNode;

export type FileStatus = 'added' | 'deleted' | 'changed' | 'renamed' | 'unchanged';

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
  oldPath: string;
  status: FileStatus;
  children?: never;
}

export const isFile = (item: FilesTreeItem): item is FileNode => !item.children;
export const isFolder = (item: FilesTreeItem): item is FolderNode => !!item.children;
