import { DatePicker } from '@gravity-ui/date-components';
import { dateTimeParse } from '@gravity-ui/date-utils';
import { RadioButton, Select, Text } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { useCallback, useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useCreateHomeworkMutation, useGetAllLecturesQuery, type Lecture } from '@api';
import { useApiError } from '@shared/utils';
import { FormWindow, Input } from '@ui';
import { useAppSelector } from 'app/hooks';
import { defaultHomework, type CreateHomework } from '../../constants';
import { createHomeworkSchema } from './createHomeworkSchema';
import styles from './CreateHomeworkWindow.module.scss';

interface CreateHomeworkWindowProps {
  open: boolean;
}

const getLecturesOptions = (userId: number | undefined, lectures: Lecture[]) =>
  userId !== undefined
    ? lectures
        .filter(lecture => lecture.lector.userId === userId)
        .map(lecture => ({ value: String(lecture.lectureId), content: lecture.name }))
    : [];

export const CreateHomeworkWindow = ({ open }: CreateHomeworkWindowProps) => {
  const navigate = useNavigate();
  const userInfo = useAppSelector(state => state.user.authData);

  const [createHomework, { isLoading, isSuccess, error: createHomeworkError }] = useCreateHomeworkMutation();
  useApiError(createHomeworkError, { name: 'createHomework', title: 'Не удалось создать домашнее задание' });

  const { data: allLectures, error: allLecturesError } = useGetAllLecturesQuery();
  useApiError(allLecturesError, { name: 'loadAllLectures', title: 'Ошибка при загрузке списка лекций' });

  const lecturesOptions = getLecturesOptions(userInfo?.userId, allLectures?.data ?? []);

  const {
    reset,
    control,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<CreateHomework>({
    resolver: yupResolver(createHomeworkSchema),
    mode: 'all',
    defaultValues: defaultHomework,
    disabled: isLoading,
    values: {
      name: defaultHomework.name,
      topic: defaultHomework.topic,
      description: defaultHomework.description,
      repositoryLink: defaultHomework.repositoryLink,
      lectureId: defaultHomework.lectureId,
      startDate: defaultHomework.startDate,
      completionDeadline: defaultHomework.completionDeadline,
      reviewDuration: defaultHomework.reviewDuration,
    },
  });

  const saveHomework = handleSubmit(data => {
    createHomework({ homeworkPost: { ...data, lectureId: Number(data.lectureId) } });
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
      title="Создание задания"
      saveText="Создать"
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
          name="lectureId"
          control={control}
          render={({ field }) => (
            <Select
              filterable
              {...field}
              value={field.value === null ? [''] : [String(field.value)]}
              options={lecturesOptions}
              label="Лекция"
              size="l"
              validationState={errors.lectureId?.message ? 'invalid' : undefined}
              errorMessage={errors.lectureId?.message}
              onUpdate={values => values[0] && field.onChange(+values[0])}
            />
          )}
        />
        <Controller
          name="repositoryLink"
          control={control}
          render={({ field }) => (
            <Input
              {...field}
              label="Ссылка на репозиторий"
              size="l"
              hasError={!!errors.repositoryLink?.message}
              errorMessage={errors.repositoryLink?.message}
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
              value={field.value.toString()}
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
