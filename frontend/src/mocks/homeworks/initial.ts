import { type Author, type Lecture } from '@domains/__generated__';
import { type Homework } from '@domains/custom';

export const initialHomeworks: Homework[] = [
  {
    id: 1,
    name: 'Git',
    description: '',
    authorId: 1,
    lectureId: 1,
    topic: 'git',
    departments: ['frontend', 'backend'],
    startDate: '2024-03-03T10:29:28.265Z',
    completionDeadline: '2024-03-25T10:29:28.265Z',
    repositoryLink: '',
    reviewDuraion: 48,
  },
  {
    id: 2,
    name: 'React',
    description: 'Сделайте что-нибудь полезное на react',
    authorId: 2,
    lectureId: 2,
    topic: 'react',
    departments: ['frontend'],
    startDate: '2024-03-04T10:29:28.265Z',
    completionDeadline: '2024-03-26T10:29:28.265Z',
    repositoryLink: 'https://github.com/AVor0n/hh-react-hw',
    reviewDuraion: 48,
  },
];

export const initialAuthors: Author[] = [
  {
    id: 1,
    firstName: 'Иван',
    lastName: 'Иванов',
  },
  {
    id: 2,
    firstName: 'Петр',
    lastName: 'Петров',
  },
];

export const initialLectures: Lecture[] = [
  {
    id: 1,
    name: 'Git',
  },
  {
    id: 2,
    name: 'React',
  },
];
