import { DatePicker } from '@gravity-ui/date-components';
import { dateTimeParse } from '@gravity-ui/date-utils';
import { RadioButton, Text } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { useCallback, useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { type HomeworkPatch, type Homework, useUpdateHomeworkMutation } from '@api';
import { useApiError } from '@shared/utils';
import { FormWindow, Input } from '@ui';
import { defaultHomework } from '../../constants';
import { editHomeworkSchema } from './editHomeworkSchema';
import styles from './EditHomeworkWindow.module.scss';

interface EditHomeworkWindowProps {
  record?: Partial<Homework>;
  open: boolean;
}

export const EditHomeworkWindow = ({ record, open }: EditHomeworkWindowProps) => {
  const [updateHomework, { isLoading, isSuccess, error }] = useUpdateHomeworkMutation();
  useApiError(error, { name: 'editHomework', title: 'Не удалось изменить домашнее задание' });
  const navigate = useNavigate();
  const {
    reset,
    control,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<HomeworkPatch>({
    resolver: yupResolver(editHomeworkSchema),
    mode: 'all',
    defaultValues: defaultHomework,
    disabled: isLoading,
    values: {
      name: record?.name ?? defaultHomework.name,
      topic: record?.topic ?? defaultHomework.topic,
      description: record?.description ?? defaultHomework.description,
      reviewDuration: record?.reviewDuration ?? defaultHomework.reviewDuration,
      startDate: record?.startDate ?? defaultHomework.startDate,
      completionDeadline: record?.completionDeadline ?? defaultHomework.completionDeadline,
    },
  });

  const saveHomework = handleSubmit(data => {
    if (!record?.id) return;
    updateHomework({ homeworkId: record.id, homeworkPatch: data });
  });

  const closeWindow = useCallback(() => {
    reset();
    navigate('/homeworks');
  }, [navigate, reset]);

  useEffect(() => {
    if (isSuccess) closeWindow();
  }, [closeWindow, isSuccess]);

  return (
    <FormWindow
      title="Редактирование задания"
      open={open}
      onClose={closeWindow}
      onSubmit={saveHomework}
      saveDisabled={!isValid}
      saveLoading={isLoading}
      cancelDisabled={isLoading}
    >
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
        <Controller
          name="startDate"
          control={control}
          render={({ field }) => (
            <DatePicker
              {...field}
              value={dateTimeParse(field.value)}
              onUpdate={value => field.onChange(value?.toDate())}
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
              {...field}
              value={dateTimeParse(field.value)}
              onUpdate={value => field.onChange(value?.toDate())}
              label="Дедлайн выполнения"
              size="l"
              validationState={errors.completionDeadline?.message ? 'invalid' : undefined}
              errorMessage={errors.completionDeadline?.message}
            />
          )}
        />
        <Text variant="subheader-1">Дедлайн проверки</Text>
        <Controller
          name="reviewDuration"
          control={control}
          render={({ field }) => (
            <RadioButton
              {...field}
              value={field.value?.toString()}
              size="l"
              options={[
                { value: '24', content: '24ч' },
                { value: '48', content: '48ч' },
              ]}
            />
          )}
        />
      </div>
    </FormWindow>
  );
};
