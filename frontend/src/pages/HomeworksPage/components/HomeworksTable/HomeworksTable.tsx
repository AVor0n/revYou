import { Pencil, TrashBin } from '@gravity-ui/icons';
import { Table, withTableSorting, withTableActions, Icon, DropdownMenu } from '@gravity-ui/uikit';
import { useNavigate } from 'react-router-dom';
import { type Homework } from '@domains';
import { deleteHomework, loadHomeworks, useAppDispatch } from 'app';
import { homeworksColumns } from './HomeworksColumns';
import styles from './HomeworksTable.module.scss';

interface HomeworksTableProps {
  data: Homework[];
  onRowClick?: (item: Homework) => void;
}

const TableWithSort = withTableSorting<Homework>(Table);
const TableWithSortAndActions = withTableActions<Homework>(TableWithSort);

export const HomeworksTable = ({ data, onRowClick }: HomeworksTableProps) => {
  const dispatch = useAppDispatch();
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
                action: async () => {
                  if (!item.id) return;
                  await dispatch(deleteHomework(item.id));
                  dispatch(loadHomeworks());
                },
              },
            ]}
          />
        </div>
      )}
    />
  );
};
