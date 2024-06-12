import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { type SignInRequest } from '@api';
import { Input } from '@ui';
import styles from './Form.module.scss';

interface SignInFormProps {
  onChangeAuthType: () => void;
  onSubmit: (data: SignInRequest) => void;
}

const schemaSignIn = yup
  .object({
    username: yup.string().required(),
    password: yup.string().required(),
  })
  .required();

export const SignInForm = ({ onChangeAuthType, onSubmit }: SignInFormProps) => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schemaSignIn),
    defaultValues: { username: '', password: '' },
  });
  const onSubmitSignIn = handleSubmit(data => {
    onSubmit(data);
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
        <Button type="submit" view="action" size="l">
          Войти
        </Button>
        <Button onClick={onChangeAuthType} view="flat-info" size="xs">
          Еще нет аккаунта? Зарегистрироваться
        </Button>
      </div>
    </form>
  );
};
