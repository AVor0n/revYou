import { DatePicker } from '@gravity-ui/date-components';
import { dateTimeParse } from '@gravity-ui/date-utils';
import { RadioButton, Select, Text } from '@gravity-ui/uikit';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { FormWindow } from '@components/FormWindow';
import { Input } from '@components/Input';
import { type Lecture } from '@domains';
import { defaultHomework, type CreateHomework } from '@pages/HomeworksPage/constants';
import {
  createHomework,
  getAllLectures,
  getAuthData,
  homeworkActions,
  loadHomeworks,
  loadLectures,
  useAppDispatch,
} from 'app';
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
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const userInfo = useSelector(getAuthData);
  const allLectures = useSelector(getAllLectures);
  const lecturesOptions = getLecturesOptions(userInfo?.userId, allLectures);

  const {
    reset,
    control,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<CreateHomework>({
    resolver: yupResolver(createHomeworkSchema),
    mode: 'all',
    defaultValues: defaultHomework,
    disabled: loading,
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

  useEffect(() => {
    dispatch(loadLectures());
  }, [dispatch]);

  const closeWindow = () => {
    reset();
    dispatch(homeworkActions.setHomeworkForEdit(null));
    navigate('/homeworks');
  };

  const saveHomework = handleSubmit(async data => {
    setLoading(true);
    return dispatch(createHomework({ ...data, lectureId: Number(data.lectureId) }))
      .unwrap()
      .then(() => {
        closeWindow();
        dispatch(loadHomeworks());
      })
      .finally(() => {
        setLoading(false);
      });
  });

  return (
    <FormWindow
      title="Создание задания"
      saveText="Создать"
      open={open}
      onClose={closeWindow}
      onSubmit={saveHomework}
      saveDisabled={!isValid}
      saveLoading={loading}
      cancelDisabled={loading}
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
