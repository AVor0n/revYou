import type { CommentsThread, Review } from '@api';

export interface ReviewSchema {
  filesCache: Record<string, Record<string, string | null>>;
  reviewInfo: Review | null;
  filesTree: FilesTree | null;
  threads: CommentsThread[] | null;
  requestInProgress: Record<string, boolean>;
  error: string;
}

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
