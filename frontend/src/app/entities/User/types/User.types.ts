// type UserRole = 'Admin' | 'Student' | 'Teacher';

export interface UserAuthDataType {
  userId: number;
  username: string;
  email: string;
}

export interface UserSchema {
  authData: UserAuthDataType;
  isAuth: boolean;
  error: string;
}
