import { type Author, type Lecture } from '@domains/__generated__';
import { type Homework } from '@domains/custom';

export const initialHomeworks: Homework[] = [
  {
    id: 1,
    name: 'react',
    description: '',
    authorId: 1,
    lectureId: 1,
    topic: 'react',
    startDate: '2024-03-03T10:29:28.265Z',
    completionDeadline: '2024-03-25T10:29:28.265Z',
    repositoryLink: '',
    reviewDuraion: 48,
  },
];

export const initialAuthors: Author[] = [
  {
    id: 1,
    firstName: 'Иван',
    lastName: 'Иванов',
  },
];

export const initialLectures: Lecture[] = [
  {
    id: 1,
    name: 'Git',
  },
];
