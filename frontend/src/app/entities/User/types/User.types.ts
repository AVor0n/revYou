import { type AuthSchema } from '@pages/AuthPage';

type UserRole = 'Admin' | 'Student' | 'Teacher';

export type UserAuthDataType = {
  role: UserRole;
} & AuthSchema;

export interface UserSchema {
  authData: UserAuthDataType;
  isAuth: boolean;
  error: string;
}
