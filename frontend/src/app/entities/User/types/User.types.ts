import { type User } from '@domains';

export interface UserSchema {
  authData: User;
  error: string;
}
