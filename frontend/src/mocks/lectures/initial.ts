import { type Lecture } from '@domains';

export const initialLectures: Lecture[] = [
  {
    lectureId: 1,
    name: 'Git',
    lector: {
      username: 'AVor0n',
      email: 'puiuz@example.com',
      userId: 2,
    },
    lectureDate: '2024-03-04T10:29:28.265Z',
    presentationLink: '',
    zoomLink: '',
  },
];
