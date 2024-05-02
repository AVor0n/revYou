import { type User } from '@domains';

export type Role = '[STUDENT]' | '[TEACHER]' | null;

export interface UserSchema {
  authData: User;
  role: Role;
  error: string;
}
