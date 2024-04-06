import * as yup from 'yup';
import type { PatchHomework } from '@domains/__generated__';

export const editHomeworkSchema: yup.ObjectSchema<PatchHomework> = yup.object({
  name: yup.string().required(),
  topic: yup.string().required(),
  description: yup.string().optional(),
  repositoryLink: yup.string().optional(),
  startDate: yup.date().default(new Date()).required() as unknown as yup.Schema<string>,
  completionDeadline: yup.date().required().min(yup.ref('startDate')) as unknown as yup.Schema<string>,
  reviewDuraion: yup.number().required().min(1),
} satisfies Record<keyof PatchHomework, unknown>);
