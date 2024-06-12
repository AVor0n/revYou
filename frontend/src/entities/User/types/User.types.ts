import { type SignInResponse } from '@api';

export type FullUserInfo = Omit<SignInResponse, 'accessToken' | 'refreshToken'>;

export interface UserSchema {
  authData: FullUserInfo | null;
  error: string;
}
