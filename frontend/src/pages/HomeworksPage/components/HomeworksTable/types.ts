import { type TableColumnConfig } from '@gravity-ui/uikit';
import { type Homework } from '@domains';

export interface HomeworkTableColumn extends TableColumnConfig<Homework> {
  meta?: {
    defaultSortOrder?: 'asc' | 'desc';
    sort?: boolean | ((itemA: Homework, itemB: Homework) => number);
  };
}
