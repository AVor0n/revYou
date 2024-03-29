import { type Homework } from './types';

export const mockHomeworks: Homework[] = [
  {
    id: '1',
    title: 'react',
    author: 'Неунывающий Леопард',
    department: ['frontend'],
    topic: 'react',
    startDate: '2024-03-03T10:29:28.265Z',
    endDate: '2024-03-25T10:29:28.265Z',
  },
  {
    id: '2',
    title: 'git',
    author: 'Ныряющий Заяц',
    department: ['frontend', 'backend'],
    topic: 'react',
    startDate: '2024-03-04T15:12:20.265Z',
    endDate: '2024-03-20T15:12:20.265Z',
  },
  {
    id: '3',
    title: 'maven',
    author: 'Летящий Пингвин',
    department: ['backend'],
    topic: 'maven',
    startDate: '2024-02-25T12:35:18.265Z',
    endDate: '2024-03-12T12:35:18.265Z',
  },
];
