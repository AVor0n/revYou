import { dateTimeParse } from '@gravity-ui/date-utils';
import { type HomeworkPost } from '@domains';

export const defaultHomework: HomeworkPost = {
  name: '',
  topic: '',
  description: '',
  repositoryLink: '',
  lectureId: 1,
  startDate: dateTimeParse('now')?.startOf('day').toISOString() ?? '',
  completionDeadline: dateTimeParse('now+14d')?.startOf('day').toISOString() ?? '',
  reviewDuration: 48,
};
