import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { Input } from '@components/index';
import { useAppDispatch } from 'app';
import { loginUser } from 'app/entities';
import styles from './AuthTab.module.scss';

const schema = yup
  .object({
    login: yup.string().required(),
    password: yup.string().required(),
  })
  .required();

export const AuthTab = () => {
  const dispatch = useAppDispatch();
  const nav = useNavigate();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
    defaultValues: { login: '', password: '' },
  });

  const onSubmit = handleSubmit(async data => {
    const res = await dispatch(loginUser(data));
    if (res.meta.requestStatus === 'fulfilled') {
      nav('/');
    }
  });

  return (
    <div className={styles.root}>
      <form onSubmit={onSubmit} className={styles.form}>
        <div className={styles.inputs}>
          <Controller
            name="login"
            control={control}
            render={({ field }) => (
              <Input {...field} label="Логин" hasError={!!errors.login?.message} errorMessage={errors.login?.message} />
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
        <Button type="submit">Войти</Button>
      </form>
    </div>
  );
};
