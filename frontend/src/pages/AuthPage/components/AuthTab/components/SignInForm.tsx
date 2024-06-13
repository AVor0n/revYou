import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { useSignInMutation } from '@api';
import { useAppDispatch, useAuth, userActions } from '@app';
import { Input } from '@ui';
import styles from './Form.module.scss';

interface SignInFormProps {
  onChangeAuthType: () => void;
}

const schemaSignIn = yup
  .object({
    username: yup.string().required(),
    password: yup.string().required(),
  })
  .required();

export const SignInForm = ({ onChangeAuthType }: SignInFormProps) => {
  const dispatch = useAppDispatch();
  const nav = useNavigate();
  const { setAccessToken, setRefreshToken } = useAuth();
  const [signIn, { data: signInData, isLoading }] = useSignInMutation();

  useEffect(() => {
    if (!signInData) return;

    localStorage.setItem('userInfo', JSON.stringify(signInData));
    dispatch(userActions.setUserInfo(signInData));
    setRefreshToken(signInData.refreshToken);
    setAccessToken(signInData.accessToken);
    nav('/homeworks');
  }, [dispatch, nav, setAccessToken, setRefreshToken, signInData]);

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schemaSignIn),
    disabled: isLoading,
    defaultValues: { username: '', password: '' },
  });

  const onSubmitSignIn = handleSubmit(data => {
    signIn({ signInRequest: data });
  });

  return (
    <form onSubmit={onSubmitSignIn} className={styles.form}>
      <div className={styles.inputs}>
        <Controller
          name="username"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              label="Логин"
              hasError={!!errors.username?.message}
              errorMessage={errors.username?.message}
            />
          )}
        />
        <Controller
          name="password"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              type="password"
              label="Пароль"
              hasError={!!errors.password?.message}
              errorMessage={errors.password?.message}
            />
          )}
        />
      </div>
      <div className={styles.buttonsWrapper}>
        <Button type="submit" view="action" size="l" loading={isLoading}>
          Войти
        </Button>
        <Button onClick={onChangeAuthType} view="flat-info" size="xs">
          Еще нет аккаунта? Зарегистрироваться
        </Button>
      </div>
    </form>
  );
};
