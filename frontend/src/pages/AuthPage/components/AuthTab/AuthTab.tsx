import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useSelector } from 'react-redux';
import * as yup from 'yup';
import { Input } from '@components/index';
import { useAppDispatch } from 'hooks';
import { getAuthLogin, getAuthPassword } from '../../selectors/getAuthData';
import { authActions } from '../../slice/Auth.slice';
import styles from './AuthTab.module.scss';

const schema = yup
  .object({
    login: yup.string().required(),
    password: yup.string().required(),
  })
  .required();

export const AuthTab = () => {
  const dispatch = useAppDispatch();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  });

  const login = useSelector(getAuthLogin);
  const password = useSelector(getAuthPassword);

  const onLoginChange = (value: string) => {
    dispatch(authActions.setAuthLogin(value));
  };

  const onPasswordChange = (value: string) => {
    dispatch(authActions.setAuthPassword(value));
  };

  const onSubmit = handleSubmit(() => {});

  return (
    <div className={styles.root}>
      <form onSubmit={onSubmit} className={styles.form}>
        <div className={styles.inputs}>
          <Controller
            name="login"
            control={control}
            render={({ field }) => (
              <Input
                {...field}
                onChange={onLoginChange}
                value={login}
                label="Логин"
                hasError={!!errors.login?.message}
                errorMessage={errors.login?.message}
              />
            )}
          />
          <Controller
            name="password"
            control={control}
            render={({ field }) => (
              <Input
                {...field}
                onChange={onPasswordChange}
                value={password}
                type="password"
                label="Пароль"
                hasError={!!errors.password?.message}
                errorMessage={errors.password?.message}
              />
            )}
          />
        </div>
        <Button type="submit">Войти</Button>
      </form>
    </div>
  );
};
