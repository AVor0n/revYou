import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { Input } from '@components/Input';
import { createSolution, loadMySolutions, useAppDispatch } from 'app';
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
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(sendSolution),
    defaultValues: { branchLink: '' },
  });

  const dispatch = useAppDispatch();

  const onSendSolution = handleSubmit(async data => {
    await dispatch(createSolution({ homeworkId, branchLink: data.branchLink })).unwrap();
    await dispatch(loadMySolutions(homeworkId)).unwrap();
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
      <Button type="submit" view="action" size="m">
        Отправить решение
      </Button>
    </form>
  );
};
