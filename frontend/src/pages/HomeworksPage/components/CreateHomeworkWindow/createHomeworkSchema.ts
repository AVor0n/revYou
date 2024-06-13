import * as yup from 'yup';
import { type CreateHomework } from '../../constants';
import type { HomeworkPost } from '@api';

export const createHomeworkSchema: yup.ObjectSchema<CreateHomework> = yup.object({
  name: yup.string().required(),
  topic: yup.string().required(),
  description: yup.string().optional(),
  repositoryLink: yup.string().required(),
  startDate: yup.date().default(new Date()).required() as unknown as yup.Schema<string>,
  completionDeadline: yup.date().required().min(yup.ref('startDate')) as unknown as yup.Schema<string>,
  reviewDuration: yup.number().required() as yup.Schema<CreateHomework['reviewDuration']>,
  lectureId: yup.number().required(),
} satisfies Record<keyof HomeworkPost, unknown>);
