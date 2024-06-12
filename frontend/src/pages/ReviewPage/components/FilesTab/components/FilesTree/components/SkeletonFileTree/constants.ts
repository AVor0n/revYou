import { type TreeNode } from '@ui';

export const skeletonTreeData: TreeNode[] = [
  {
    id: '1',
    children: [
      {
        id: '1.1',
        children: [
          {
            id: '1.1.1',
          },
          {
            id: '1.1.2',
          },
        ],
      },
      {
        id: '1.2',
      },
      {
        id: '1.3',
      },
    ],
  },
  {
    id: '2',
    children: [
      {
        id: '2.1',
        children: [
          {
            id: '2.1.1',
          },
          {
            id: '2.1.2',
          },
        ],
      },
      {
        id: '2.2',
      },
      {
        id: '2.3',
        children: [
          {
            id: '2.3.1',
            children: [
              {
                id: '2.3.1.1',
                children: [
                  {
                    id: '2.3.1.1.1',
                  },
                ],
              },
            ],
          },
        ],
      },
    ],
  },
];
