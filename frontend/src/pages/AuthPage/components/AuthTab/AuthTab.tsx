import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { type SignInRequest, type SignUpRequest } from '@domains';
import { useAppDispatch, useAuth } from 'app';
import { signInUser, signUpUser } from 'app/entities';
import { SignInForm, SignUpForm } from './components';
import styles from './AuthTab.module.scss';

export const AuthTab = () => {
  const dispatch = useAppDispatch();
  const nav = useNavigate();
  const { setToken } = useAuth();

  const [authType, setAuthType] = useState<'signIn' | 'signUp'>('signIn');

  const onSubmitSignIn = (data: SignInRequest) => {
    dispatch(signInUser(data))
      .unwrap()
      .then(({ accessToken }) => {
        setToken(accessToken ?? '');
        nav('/homeworks');
      });
  };

  const onSubmitSignUp = (data: SignUpRequest) => {
    dispatch(signUpUser(data))
      .unwrap()
      .then(() => {
        onSubmitSignIn({
          username: data.username,
          password: data.password,
        });
      });
  };

  const onChangeAuthType = () => {
    setAuthType(authType === 'signIn' ? 'signUp' : 'signIn');
  };

  return (
    <div className={styles.AuthTab}>
      {authType === 'signIn' ? (
        <SignInForm onChangeAuthType={onChangeAuthType} onSubmit={onSubmitSignIn} />
      ) : (
        <SignUpForm onChangeAuthType={onChangeAuthType} onSubmit={onSubmitSignUp} />
      )}
    </div>
  );
};
