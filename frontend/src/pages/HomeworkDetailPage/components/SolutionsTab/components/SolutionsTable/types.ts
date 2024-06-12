import { type TableColumnConfig } from '@gravity-ui/uikit';
import { type Review } from '@api';

export interface SolutionTableColumn extends TableColumnConfig<Review> {
  meta?: {
    defaultSortOrder?: 'asc' | 'desc';
    sort?: boolean | ((itemA: Review, itemB: Review) => number);
  };
}
