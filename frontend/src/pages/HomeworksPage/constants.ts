import { dateTimeParse } from '@gravity-ui/date-utils';
import type { PostHomework } from '@domains/__generated__';

export const defaultHomework: PostHomework = {
  name: '',
  topic: '',
  description: '',
  repositoryLink: '',
  authorId: 1,
  lectureId: 1,
  startDate: dateTimeParse('now')?.startOf('day').toISOString() ?? '',
  completionDeadline: dateTimeParse('now+14d')?.startOf('day').toISOString() ?? '',
  reviewDuraion: 48,
};
