import { Card } from '@gravity-ui/uikit';
import { useState } from 'react';
import { SignInForm, SignUpForm } from './components';
import styles from './AuthTab.module.scss';

export const AuthTab = () => {
  const [authType, setAuthType] = useState<'signIn' | 'signUp'>('signIn');

  const onChangeAuthType = () => {
    setAuthType(authType === 'signIn' ? 'signUp' : 'signIn');
  };

  return (
    <Card view="raised" className={styles.AuthTab}>
      {authType === 'signIn' ? (
        <SignInForm onChangeAuthType={onChangeAuthType} />
      ) : (
        <SignUpForm onChangeAuthType={onChangeAuthType} />
      )}
    </Card>
  );
};
