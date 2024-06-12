import { type Homework, type HomeworkAuthor, type HomeworkLecture } from '@api';

export type MockHomework = Omit<Homework, 'author' | 'lecture'> & {
  authorId: number;
  lectureId: number;
};

export const initialHomeworks: MockHomework[] = [
  {
    id: 1,
    name: 'Git',
    description:
      'Создать fork этого репозитория, в вашем репозитории появится его копия\nСделать интерактивный rebase ветки make-me-friendly (только её коммитов, начиная с Add bubble sort), подчистив историю:\nКоммиты, начинающихся с Fix, перенести к тем, к которым они относятся, и сделать fixup\nУдалить файл trash.bin из коммита Add bubbleSort\nПеренести коммит про gitignore в начало\nСделать мердж ветки merge-me в ветку make-me-friendly, в конфликтах выбрать любую сторону\nСделать push получившейся ветки в свой клон репозитория\nСоздать pull request этой ветки в https://github.com/gooverdian/hh-school-git-crash-course/compare, выбрав там свой форк, в качестве назначения выбирайте ветку make-me-friendly моего репозитория',
    authorId: 1,
    lectureId: 1,
    topic: 'git',
    departments: ['frontend', 'backend'],
    startDate: '2024-03-03T10:29:28.265Z',
    status: undefined,
    completionDeadline: '2024-03-25T10:29:28.265Z',
    repositoryLink: 'https://github.com/gooverdian/hh-school-git-crash-course',
    reviewDuration: 48,
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
    reviewDuration: 48,
    status: 'REVIEW_STAGE',
  },
];

export const initialAuthors: HomeworkAuthor[] = [
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

export const initialLectures: HomeworkLecture[] = [
  {
    id: 1,
    name: 'Git',
  },
  {
    id: 2,
    name: 'React',
  },
];
