import { Button, TextInput } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import styles from './AuthTab.module.scss';

const schema = yup
  .object({
    login: yup.string().required(),
    password: yup.string().required(),
  })
  .required();

export const AuthTab = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  });
  // const onSubmit = handleSubmit(data => console.log(data));
  const onSubmit = handleSubmit(() => {});

  return (
    <div className={styles.root}>
      <form onSubmit={onSubmit} className={styles.form}>
        <div className={styles.inputs}>
          <Controller
            name="login"
            control={control}
            render={({ field }) => (
              <TextInput
                {...field}
                label="Логин"
                validationState={errors.login?.message ? 'invalid' : undefined}
                errorMessage={errors.login?.message}
              />
            )}
          />
          <Controller
            name="password"
            control={control}
            render={({ field }) => (
              <TextInput
                {...field}
                type="password"
                label="Пароль"
                validationState={errors.password?.message ? 'invalid' : undefined}
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
