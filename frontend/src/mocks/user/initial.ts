import type { SignInResponse } from '@domains';

export interface MockUser extends SignInResponse {
  password: string;
}

export const initialUsers: MockUser[] = [
  {
    email: 'test@test.com',
    userId: 1,
    username: 'username_1',
    accessToken: 'token_username_1',
    refreshToken: 'token_username_1',
    password: '100',
    role: 'STUDENT',
  },
];
