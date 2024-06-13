import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { useCreateSolutionMutation } from '@shared/api';
import { Input } from '@ui';
import styles from './SendSolutionForm.module.scss';

const sendSolution = yup
  .object({
    branchLink: yup.string().required(),
  })
  .required();

interface SendSolutionFormProps {
  homeworkId: number;
}

export const SendSolutionForm = ({ homeworkId }: SendSolutionFormProps) => {
  const navigate = useNavigate();
  const [createSolution, { isLoading }] = useCreateSolutionMutation();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(sendSolution),
    defaultValues: { branchLink: '' },
    disabled: isLoading,
  });

  const onSendSolution = handleSubmit(data => {
    createSolution({ homeworkId, solutionPost: data });
    navigate(`/homeworks/${homeworkId}/my-solution`);
  });

  return (
    <form onSubmit={onSendSolution} className={styles.form}>
      <Controller
        name="branchLink"
        control={control}
        render={({ field }) => (
          <Input
            {...field}
            label="Ссылка на решение"
            hasError={!!errors.branchLink?.message}
            errorMessage={errors.branchLink?.message}
          />
        )}
      />
      <Button type="submit" view="action" size="m" loading={isLoading}>
        Отправить решение
      </Button>
    </form>
  );
};
