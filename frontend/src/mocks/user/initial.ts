import type { SignInResponse } from '@domains';

export interface MockUser extends SignInResponse {
  password: string;
}

export const initialUsers: MockUser[] = [
  {
    email: 'test@test.com',
    userId: 1,
    username: 'student_1',
    accessToken: 'token_student_1',
    refreshToken: 'token_student_1',
    password: '100',
    role: 'STUDENT',
  },
  {
    email: 'test@test.com',
    userId: 2,
    username: 'teacher_1',
    accessToken: 'token_teacher_1',
    refreshToken: 'token_teacher_1',
    password: '100',
    role: 'TEACHER',
  },
];
