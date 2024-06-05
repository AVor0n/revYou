import type { CommentsThread, Review } from '@domains';

export interface ReviewSchema {
  filesCache: Record<string, Record<string, string | null>>;
  reviewInfo: Review | null;
  filesTree: FilesTree | null;
  activeFilePath: string;
  threads: CommentsThread[] | null;
  requestInProgress: Record<string, boolean>;
  error: string;
}

export type FilesTree = FilesTreeItem[];

export type FilesTreeItem = FolderNode | FileNode;

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

export const isFile = (item: FilesTreeItem): item is FileNode => !item.children;
export const isFolder = (item: FilesTreeItem): item is FolderNode => !!item.children;
