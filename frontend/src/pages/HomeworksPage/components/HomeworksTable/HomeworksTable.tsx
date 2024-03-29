import { TrashBin } from '@gravity-ui/icons';
import { Table, withTableSorting, withTableActions, Icon } from '@gravity-ui/uikit';
import { type Homework } from '../../types';
import { homeworksColumns } from './HomeworksColumns';
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
    getRowActions={item => [
      {
        text: 'Удалить',
        theme: 'danger',
        icon: <Icon data={TrashBin} />,
        handler: () => alert(`Удаление элемента ${item.id}: ${item.title}`),
      },
    ]}
  />
);
