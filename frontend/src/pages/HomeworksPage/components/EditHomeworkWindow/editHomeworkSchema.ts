import * as yup from 'yup';
import type { HomeworkPatch } from '@api';

export const editHomeworkSchema: yup.ObjectSchema<HomeworkPatch> = yup.object({
  name: yup.string().required(),
  topic: yup.string().required(),
  description: yup.string().optional(),
  startDate: yup.date().default(new Date()).required() as unknown as yup.Schema<string>,
  completionDeadline: yup.date().required().min(yup.ref('startDate')) as unknown as yup.Schema<string>,
  reviewDuration: yup.number().required() as yup.Schema<HomeworkPatch['reviewDuration']>,
} satisfies Record<keyof HomeworkPatch, unknown>);
