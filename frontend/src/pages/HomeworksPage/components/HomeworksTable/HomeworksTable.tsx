import { Table, withTableSorting, withTableActions } from '@gravity-ui/uikit';
import { type Homework } from '@domains';
import { homeworksColumns } from './HomeworksColumns';
import { HomeworksRowActions } from './HomeworksRowActions';
import styles from './HomeworksTable.module.scss';

interface HomeworksTableProps {
  data: Homework[];
  onRowClick?: (item: Homework) => void;
}

const TableWithSort = withTableSorting<Homework>(Table);
const TableWithSortAndActions = withTableActions<Homework>(TableWithSort);

export const HomeworksTable = ({ data, onRowClick }: HomeworksTableProps) => (
  <TableWithSortAndActions
    className={styles.table}
    columns={homeworksColumns}
    data={data}
    emptyMessage="Нет данных"
    onRowClick={onRowClick}
    renderRowActions={props => <HomeworksRowActions {...props} />}
  />
);
