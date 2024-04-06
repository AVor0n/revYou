import { DatePicker } from '@gravity-ui/date-components';
import { dateTimeParse } from '@gravity-ui/date-utils';
import { yupResolver } from '@hookform/resolvers/yup';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { FormWindow } from '@components/FormWindow';
import { Input } from '@components/Input';
import { type PostHomework } from '@domains/__generated__';
import { defaultHomework } from '@pages/HomeworksPage/constants';
import { createHomework, homeworkActions, loadHomeworks, useAppDispatch } from 'app';
import { createHomeworkSchema } from './createHomeworkSchema';
import styles from './CreateHomeworkWindow.module.scss';

interface CreateHomeworkWindowProps {
  open: boolean;
}

export const CreateHomeworkWindow = ({ open }: CreateHomeworkWindowProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const {
    reset,
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<PostHomework>({
    resolver: yupResolver(createHomeworkSchema),
    mode: 'all',
    defaultValues: defaultHomework,
    values: {
      name: defaultHomework.name,
      topic: defaultHomework.topic,
      description: defaultHomework.description,
      repositoryLink: defaultHomework.repositoryLink,
      authorId: defaultHomework.authorId,
      lectureId: defaultHomework.lectureId,
      startDate: defaultHomework.startDate,
      completionDeadline: defaultHomework.completionDeadline,
      reviewDuraion: defaultHomework.reviewDuraion,
    },
  });

  const closeWindow = () => {
    reset();
    dispatch(homeworkActions.setHomeworkForEdit(null));
    navigate('/homeworks');
  };

  const saveHomework = handleSubmit(async data => {
    await dispatch(createHomework(data));
    closeWindow();
    await dispatch(loadHomeworks());
  });

  return (
    <FormWindow title="Создание задания" saveText="Создать" open={open} onClose={closeWindow} onSubmit={saveHomework}>
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
        <Input label="Автор" value="1" size="l" />
        <Input label="Лекция" value="1" size="l" />
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
              value={field.value.toString()}
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
