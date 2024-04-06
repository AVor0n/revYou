import * as yup from 'yup';
import type { PostHomework } from '@domains/__generated__';

export const createHomeworkSchema: yup.ObjectSchema<PostHomework> = yup.object({
  name: yup.string().required(),
  topic: yup.string().required(),
  description: yup.string().optional(),
  repositoryLink: yup.string().optional(),
  startDate: yup.date().default(new Date()).required() as unknown as yup.Schema<string>,
  completionDeadline: yup.date().required().min(yup.ref('startDate')) as unknown as yup.Schema<string>,
  reviewDuraion: yup.number().required().min(1),
  authorId: yup.number().required(),
  lectureId: yup.number().required(),
} satisfies Record<keyof PostHomework, unknown>);
