import { Table, withTableSorting } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { type Review } from '@domains';
import { solutionsColumns } from './MySolutionsColumns';
import styles from './MySolutionsTable.module.scss';

interface MySolutionsTableProps {
  data: Review[];
  onRowClick?: (item: Review) => void;
}

const TableWithSort = withTableSorting<Review>(Table);

export const MySolutionsTable = ({ data, onRowClick }: MySolutionsTableProps) => {
  const interactiveStatuses = ['CORRECTIONS_REQUIRED', 'CORRECTIONS_LOADED', 'APPROVED'];

  return (
    <TableWithSort
      className={styles.table}
      columns={solutionsColumns}
      data={data}
      getRowDescriptor={({ status }) => ({
        classNames: [clsx(styles.row, status && interactiveStatuses.includes(status) && styles.interactive)],
      })}
      emptyMessage="Нет данных"
      onRowClick={item => {
        if (!item.status || !interactiveStatuses.includes(item.status)) return;
        onRowClick?.(item);
      }}
    />
  );
};
