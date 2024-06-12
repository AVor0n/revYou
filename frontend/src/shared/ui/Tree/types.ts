// eslint-disable-next-line @typescript-eslint/no-explicit-any
export interface TreeNode<T extends TreeNode = any> {
  id: string;
  children?: T[];
}
