import { Table, withTableSorting } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { useParams } from 'react-router-dom';
import { type Review } from '@domains';
import { getSolutionsColumns } from './SolutionsColumns';
import styles from './SolutionsTable.module.scss';

interface SolutionsTableProps {
  data: Review[];
  onRowClick?: (item: Review) => void;
}

const TableWithSort = withTableSorting<Review>(Table);

export const SolutionsTable = ({ data, onRowClick }: SolutionsTableProps) => {
  const nonInteractiveStatuses = ['REVIEWER_FOUND', 'REVIEWER_SEARCH'];
  const { homeworkId } = useParams<{ homeworkId: string }>();

  if (homeworkId === undefined) {
    throw new Error('not provided homeworkId');
  }

  return (
    <TableWithSort
      className={styles.table}
      columns={getSolutionsColumns(+homeworkId)}
      data={data}
      getRowDescriptor={({ status }) => ({
        classNames: [clsx(styles.row, status && !nonInteractiveStatuses.includes(status) && styles.interactive)],
      })}
      emptyMessage="Нет данных"
      onRowClick={item => {
        if (!item.status || nonInteractiveStatuses.includes(item.status)) return;
        onRowClick?.(item);
      }}
    />
  );
};
