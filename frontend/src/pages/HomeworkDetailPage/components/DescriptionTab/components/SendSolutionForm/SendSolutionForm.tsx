import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { Input } from '@components/Input';
import styles from './SendSolutionForm.module.scss';

const sendSolution = yup
  .object({
    solution: yup.string().required(),
  })
  .required();

export const SendSolutionForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(sendSolution),
    defaultValues: { solution: '' },
  });
  const onSendSolution = handleSubmit(() => {
    // onSubmit(data);
  });

  return (
    <form onSubmit={onSendSolution} className={styles.form}>
      <Controller
        name="solution"
        control={control}
        render={({ field }) => (
          <Input
            {...field}
            label="Ссылка на решение"
            hasError={!!errors.solution?.message}
            errorMessage={errors.solution?.message}
          />
        )}
      />
      <Button type="submit" view="action" size="m">
        Отправить решение
      </Button>
    </form>
  );
};
