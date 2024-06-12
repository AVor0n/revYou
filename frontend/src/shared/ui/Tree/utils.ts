import { type TreeNode } from './types';

/**
 * Обход дерева в глубину
 * @param items - Корневые узлы дерева
 * @param action - Действие которое нужно выполнить для каждого узла
 */
export const traversalTree = <T extends TreeNode, D extends T = T>(
  items: TreeNode<T>[],
  action: (item: TreeNode<T>) => void,
): D[] => {
  for (const item of items) {
    action(item);
    if (item.children) {
      traversalTree(item.children, action);
    }
  }
  return items as D[];
};

/** Возвращает массив id всех узлов дерева */
export const getAllIds = <T extends TreeNode>(tree: T[]) => {
  const allIds: string[] = [];
  traversalTree(tree, item => allIds.push(item.id));
  return allIds;
};

/**
 * Поиск элемента в дереве
 * @param items - Корневые узлы дерева
 * @param predicate - Условие которое должно выполняться для искомого элемента
 */
export const findInTree = <T extends TreeNode<T>>(items: T[], predicate: (item: T) => boolean): T | null => {
  for (const item of items) {
    if (predicate(item)) return item;
    if (item.children) {
      const result = findInTree(item.children, predicate);
      if (result) return result;
    }
  }
  return null;
};

export const sortTree = <T extends TreeNode<T>>(items: T[], sortFn: (itemA: T, itemB: T) => number): T[] =>
  traversalTree(items.sort(sortFn), item => {
    if (item.children) {
      item.children.sort(sortFn);
    }
  });
