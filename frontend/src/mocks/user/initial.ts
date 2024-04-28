import type { User } from '@domains';

export interface MockUser extends User {
  token: string;
  password: string;
}

export const initialUsers: MockUser[] = [
  {
    email: 'test@test.com',
    userId: 1,
    username: 'username_1',
    token: 'token_username_1',
    password: '100',
  },
];
