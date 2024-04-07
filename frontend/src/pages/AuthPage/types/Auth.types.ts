export interface SignUpSchema {
  username: string;
  password: string;
  email: string;
  confirmPassword: string;
}

export interface SignInSchema {
  username: string;
  password: string;
}
