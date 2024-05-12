import { Pencil, TrashBin } from '@gravity-ui/icons';
import { DropdownMenu, Icon, type RenderRowActionsProps } from '@gravity-ui/uikit';
import { type FC } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { deleteHomework, getUserRole, loadHomeworks, useAppDispatch } from 'app';
import type { Homework } from '@domains';

export const HomeworksRowActions: FC<RenderRowActionsProps<Homework>> = ({ item }) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const role = useSelector(getUserRole);

  if (role === '[STUDENT]') return null;

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
            action: async () => {
              if (!item.id) return;
              await dispatch(deleteHomework(item.id));
              dispatch(loadHomeworks());
            },
          },
        ]}
      />
    </div>
  );
};
