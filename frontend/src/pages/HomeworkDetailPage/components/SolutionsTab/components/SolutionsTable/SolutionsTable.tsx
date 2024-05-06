import { Table, withTableSorting } from '@gravity-ui/uikit';
import { type Solution } from '@domains';
import { solutionsColumns } from './SolutionsColumns';
import styles from './SolutionsTable.module.scss';

interface SolutionsTableProps {
  data: Solution[];
  onRowClick?: (item: Solution) => void;
}

const TableWithSort = withTableSorting<Solution>(Table);

export const SolutionsTable = ({ data, onRowClick }: SolutionsTableProps) => (
  <TableWithSort
    className={styles.table}
    columns={solutionsColumns}
    data={data}
    emptyMessage="Нет данных"
    onRowClick={onRowClick}
  />
);
