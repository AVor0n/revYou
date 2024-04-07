import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { type SignUpSchema, type SignInSchema } from '@pages/AuthPage';
import { useAppDispatch } from 'app';
import { signInUser, signUpUser } from 'app/entities';
import { SignInForm, SignUpForm } from './components';
import styles from './AuthTab.module.scss';

export const AuthTab = () => {
  const dispatch = useAppDispatch();
  const nav = useNavigate();

  const [authType, setAuthType] = useState<'signIn' | 'signUp'>('signIn');

  const onSubmitSignIn = async (data: SignInSchema) => {
    const res = await dispatch(signInUser(data));
    if (res.meta.requestStatus === 'fulfilled') {
      nav('/homeworks');
    }
  };

  const onSubmitSignUp = async (data: SignUpSchema) => {
    const res = await dispatch(signUpUser(data));
    if (res.meta.requestStatus === 'fulfilled') {
      nav('/homeworks');
    }
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
