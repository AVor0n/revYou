import { Pencil, TrashBin } from '@gravity-ui/icons';
import { DropdownMenu, Icon, type RenderRowActionsProps } from '@gravity-ui/uikit';
import { type FC } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDeleteHomeworkMutation, type Homework } from '@api';
import { useAppSelector } from 'app/hooks';

export const HomeworksRowActions: FC<RenderRowActionsProps<Homework>> = ({ item }) => {
  const navigate = useNavigate();
  const [deleteHomework] = useDeleteHomeworkMutation();
  const role = useAppSelector(state => state.user.authData?.role);

  if (role === 'STUDENT') return null;

  return (
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
            action: () => {
              if (!item.id) return;
              deleteHomework({ homeworkId: item.id });
            },
          },
        ]}
      />
    </div>
  );
};
