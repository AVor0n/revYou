import { Card } from '@gravity-ui/uikit';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { type SignInRequest, type SignUpRequest } from '@api';
import { useAppDispatch, useAuth } from 'app';
import { signInUser, signUpUser } from 'entities';
import { SignInForm, SignUpForm } from './components';
import styles from './AuthTab.module.scss';

export const AuthTab = () => {
  const dispatch = useAppDispatch();
  const nav = useNavigate();
  const { setAccessToken, setRefreshToken } = useAuth();

  const [authType, setAuthType] = useState<'signIn' | 'signUp'>('signIn');

  const onSubmitSignIn = (data: SignInRequest) => {
    dispatch(signInUser(data))
      .unwrap()
      .then(({ accessToken, refreshToken }) => {
        setRefreshToken(refreshToken);
        setAccessToken(accessToken);
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
    <Card view="raised" className={styles.AuthTab}>
      {authType === 'signIn' ? (
        <SignInForm onChangeAuthType={onChangeAuthType} onSubmit={onSubmitSignIn} />
      ) : (
        <SignUpForm onChangeAuthType={onChangeAuthType} onSubmit={onSubmitSignUp} />
      )}
    </Card>
  );
};
