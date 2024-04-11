import * as yup from 'yup';
import type { HomeworkPatchReviewDurationEnum, HomeworkPost } from '@domains';

export const createHomeworkSchema: yup.ObjectSchema<HomeworkPost> = yup.object({
  name: yup.string().required(),
  topic: yup.string().required(),
  description: yup.string().optional(),
  repositoryLink: yup.string().optional(),
  startDate: yup.date().default(new Date()).required() as unknown as yup.Schema<string>,
  completionDeadline: yup.date().required().min(yup.ref('startDate')) as unknown as yup.Schema<string>,
  reviewDuration: yup.number().required() as yup.Schema<HomeworkPatchReviewDurationEnum>,
  lectureId: yup.number().required(),
} satisfies Record<keyof HomeworkPost, unknown>);
