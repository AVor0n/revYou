import { Pencil, TrashBin } from '@gravity-ui/icons';
import { Table, withTableSorting, withTableActions, Icon, DropdownMenu } from '@gravity-ui/uikit';
import { useNavigate } from 'react-router-dom';
import { type GetHomework } from '@domains/__generated__';
import { homeworksColumns } from './HomeworksColumns';
import styles from './HomeworksTable.module.scss';

interface HomeworksTableProps {
  data: GetHomework[];
  onRowClick?: (item: GetHomework) => void;
}

const TableWithSort = withTableSorting<GetHomework>(Table);
const TableWithSortAndActions = withTableActions<GetHomework>(TableWithSort);

export const HomeworksTable = ({ data, onRowClick }: HomeworksTableProps) => {
  const navigate = useNavigate();

  return (
    <TableWithSortAndActions
      className={styles.table}
      columns={homeworksColumns}
      data={data}
      emptyMessage="Нет данных"
      onRowClick={onRowClick}
      renderRowActions={({ item }) => (
        <div onClick={event => event.stopPropagation()}>
          <DropdownMenu
            items={[
              {
                text: 'Редактировать',
                theme: 'normal',
                icon: <Icon data={Pencil} />,
                action: () => navigate(`/homeworks/${item.id}/edit`),
              },
              {
                text: 'Удалить',
                theme: 'danger',
                icon: <Icon data={TrashBin} />,
                action: () => alert(`Удаление элемента ${item.id}: ${item.name}`),
              },
            ]}
          />
        </div>
      )}
    />
  );
};
