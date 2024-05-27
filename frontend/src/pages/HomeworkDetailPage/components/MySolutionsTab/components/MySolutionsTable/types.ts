import { type TableColumnConfig } from '@gravity-ui/uikit';
import { type Review } from '@domains';

export interface SolutionTableColumn extends TableColumnConfig<Review> {
  meta?: {
    defaultSortOrder?: 'asc' | 'desc';
    sort?: boolean | ((itemA: Review, itemB: Review) => number);
  };
}
