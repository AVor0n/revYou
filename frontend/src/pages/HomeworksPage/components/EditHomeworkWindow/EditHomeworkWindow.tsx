import { DatePicker } from '@gravity-ui/date-components';
import { dateTimeParse } from '@gravity-ui/date-utils';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { FormWindow } from '@components/FormWindow';
import { Input } from '@components/Input';
import { type PatchHomework, type GetHomework } from '@domains/__generated__';
import { defaultHomework } from '@pages/HomeworksPage/constants';
import { editHomework, homeworkActions, loadHomeworks, useAppDispatch } from 'app';
import { editHomeworkSchema } from './editHomeworkSchema';
import styles from './EditHomeworkWindow.module.scss';

interface EditHomeworkWindowProps {
  record: GetHomework | null;
  open: boolean;
}

export const EditHomeworkWindow = ({ record, open }: EditHomeworkWindowProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const {
    reset,
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<PatchHomework>({
    resolver: yupResolver(editHomeworkSchema),
    mode: 'all',
    defaultValues: defaultHomework,
    values: {
      name: record?.name ?? defaultHomework.name,
      topic: record?.topic ?? defaultHomework.topic,
      description: record?.description ?? defaultHomework.description,
      repositoryLink: record?.repositoryLink ?? defaultHomework.repositoryLink,
      reviewDuraion: record?.reviewDuraion ?? defaultHomework.reviewDuraion,
      startDate: record?.startDate ?? defaultHomework.startDate,
      completionDeadline: record?.completionDeadline ?? defaultHomework.completionDeadline,
    },
  });

  const closeWindow = () => {
    reset();
    dispatch(homeworkActions.setHomeworkForEdit(null));
    navigate('/homeworks');
  };

  const saveHomework = handleSubmit(async data => {
    if (!record?.id) return;
    await dispatch(editHomework([record.id, data]));
    closeWindow();
    await dispatch(loadHomeworks());
  });

  return (
    <FormWindow title="Редактирование задания" open={open} onClose={closeWindow} onSubmit={saveHomework}>
      <div className={styles.content}>
        <Controller
          name="name"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              label="Название"
              size="l"
              hasError={!!errors.name?.message}
              errorMessage={errors.name?.message}
            />
          )}
        />
        <Controller
          name="topic"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              label="Тема"
              size="l"
              hasError={!!errors.topic?.message}
              errorMessage={errors.topic?.message}
            />
          )}
        />
        <Input label="Автор" value={`${record?.author?.firstName} ${record?.author?.lastName}`} size="l" disabled />
        <Controller
          name="startDate"
          control={control}
          render={({ field }) => (
            <DatePicker
              name={field.name}
              value={dateTimeParse(field.value)}
              onUpdate={value => field.onChange(value?.toDate())}
              onBlur={field.onBlur}
              label="Дата выдачи"
              size="l"
              validationState={errors.startDate?.message ? 'invalid' : undefined}
              errorMessage={errors.startDate?.message}
            />
          )}
        />
        <Controller
          name="completionDeadline"
          control={control}
          render={({ field }) => (
            <DatePicker
              name={field.name}
              value={dateTimeParse(field.value)}
              onUpdate={value => field.onChange(value?.toDate())}
              onBlur={field.onBlur}
              label="Дедлайн выполнения"
              size="l"
              validationState={errors.completionDeadline?.message ? 'invalid' : undefined}
              errorMessage={errors.completionDeadline?.message}
            />
          )}
        />
        <Controller
          name="reviewDuraion"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              value={field.value?.toString() ?? '0'}
              type="number"
              label="Дедлайн проверки"
              size="l"
              hasError={!!errors.reviewDuraion?.message}
              errorMessage={errors.reviewDuraion?.message}
            />
          )}
        />
      </div>
    </FormWindow>
  );
};
