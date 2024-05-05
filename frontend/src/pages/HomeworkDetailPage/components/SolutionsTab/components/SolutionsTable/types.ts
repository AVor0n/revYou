import { type TableColumnConfig } from '@gravity-ui/uikit';
import { type Solution } from '@domains';

export interface SolutionTableColumn extends TableColumnConfig<Solution> {
  meta?: {
    defaultSortOrder?: 'asc' | 'desc';
    sort?: boolean | ((itemA: Solution, itemB: Solution) => number);
  };
}
