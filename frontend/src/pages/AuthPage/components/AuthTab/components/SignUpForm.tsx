import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { type SignUpRequest } from '@api';
import { Input } from '@ui';
import styles from './Form.module.scss';

interface SignUpFormProps {
  onChangeAuthType: () => void;
  onSubmit: (data: SignUpRequest) => void;
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

export const SignUpForm = ({ onChangeAuthType, onSubmit }: SignUpFormProps) => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schemaSignUp),
    defaultValues: { username: '', password: '', confirmPassword: '', email: '' },
  });

  const onSubmitUp = handleSubmit(data => {
    onSubmit(data);
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
        <Button type="submit" view="action" size="l">
          Зарегистрироваться
        </Button>
        <Button onClick={onChangeAuthType} view="flat-info" size="xs">
          Уже есть аккаунт? Войти
        </Button>
      </div>
    </form>
  );
};
