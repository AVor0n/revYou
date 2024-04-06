import { type TableColumnConfig } from '@gravity-ui/uikit';
import { type GetHomework } from '@domains/__generated__';
import { type Homework } from '@domains/custom';

export interface HomeworkTableColumn extends TableColumnConfig<GetHomework> {
  meta?: {
    defaultSortOrder?: 'asc' | 'desc';
    sort?: boolean | ((itemA: Homework, itemB: Homework) => number);
  };
}
