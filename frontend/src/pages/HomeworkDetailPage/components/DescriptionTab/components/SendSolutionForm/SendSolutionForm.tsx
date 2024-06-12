import { Button } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { Input } from '@ui';
import { createSolution, loadMySolutions, useAppDispatch } from 'app';
import { useAppSelector } from 'app/hooks';
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
  const requestInProgress = useAppSelector(state => state.solution.requestInProgress);
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(sendSolution),
    defaultValues: { branchLink: '' },
    disabled: requestInProgress.sendSolution,
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
      <Button type="submit" view="action" size="m" loading={requestInProgress.sendSolution}>
        Отправить решение
      </Button>
    </form>
  );
};
