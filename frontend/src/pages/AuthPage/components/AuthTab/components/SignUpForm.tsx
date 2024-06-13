import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect, useRef } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { useSignInMutation, useSignUpMutation, type SignUpRequest } from '@api';
import { useAppDispatch, useAuth, userActions } from '@app';
import { Input } from '@ui';
import styles from './Form.module.scss';

interface SignUpFormProps {
  onChangeAuthType: () => void;
}

const schemaSignUp = yup
  .object({
    username: yup.string().required(),
    password: yup.string().required(),
    confirmPassword: yup
      .string()
      .oneOf([yup.ref('password'), undefined], 'Passwords must match')
      .required(),
    email: yup.string().email().required(),
  })
  .required();

export const SignUpForm = ({ onChangeAuthType }: SignUpFormProps) => {
  const dispatch = useAppDispatch();
  const signUpRequestData = useRef<SignUpRequest>();
  const nav = useNavigate();
  const { setAccessToken, setRefreshToken } = useAuth();
  const [signIn, { data: signInData, isLoading: signInLoading }] = useSignInMutation();
  const [signUp, { data: signUpData, isLoading: signUpLoading }] = useSignUpMutation();

  useEffect(() => {
    if (!signInData) return;

    localStorage.setItem('userInfo', JSON.stringify(signInData));
    dispatch(userActions.setUserInfo(signInData));
    setRefreshToken(signInData.refreshToken);
    setAccessToken(signInData.accessToken);
    nav('/homeworks');
  }, [dispatch, nav, setAccessToken, setRefreshToken, signInData]);

  useEffect(() => {
    if (!signUpData || !signUpRequestData.current) return;

    signIn({ signInRequest: signUpRequestData.current });
  }, [signIn, signUpData]);

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schemaSignUp),
    disabled: signInLoading || signUpLoading,
    defaultValues: { username: '', password: '', confirmPassword: '', email: '' },
  });

  const onSubmitUp = handleSubmit(data => {
    signUpRequestData.current = data;
    signUp({ signUpRequest: data });
  });

  return (
    <form onSubmit={onSubmitUp} className={styles.form}>
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
          name="email"
          control={control}
          render={({ field }) => (
            <Input {...field} label="Email" hasError={!!errors.email?.message} errorMessage={errors.email?.message} />
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
        <Controller
          name="confirmPassword"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              type="password"
              label="Подтвердите пароль"
              hasError={!!errors.confirmPassword?.message}
              errorMessage={errors.confirmPassword?.message}
            />
          )}
        />
      </div>
      <div className={styles.buttonsWrapper}>
        <Button type="submit" view="action" size="l" loading={signInLoading || signUpLoading}>
          Зарегистрироваться
        </Button>
        <Button onClick={onChangeAuthType} view="flat-info" size="xs">
          Уже есть аккаунт? Войти
        </Button>
      </div>
    </form>
  );
};
