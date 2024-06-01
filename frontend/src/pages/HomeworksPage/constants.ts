import { dateTimeParse } from '@gravity-ui/date-utils';
import { type HomeworkPost } from '@domains';

export type CreateHomework = Omit<HomeworkPost, 'lectureId'> & { lectureId: number | null };

export const defaultHomework: CreateHomework = {
  name: '',
  topic: '',
  description: '',
  repositoryLink: '',
  lectureId: null,
  startDate: dateTimeParse('now')?.startOf('day').toISOString() ?? '',
  completionDeadline: dateTimeParse('now+14d')?.startOf('day').toISOString() ?? '',
  reviewDuration: 48,
};
