import { ArrowRightFromSquare } from '@gravity-ui/icons';
import { Button, DropdownMenu, Icon } from '@gravity-ui/uikit';
import { useNavigate } from 'react-router-dom';
import { userActions } from '@entities';
import { useAppDispatch, useAppSelector } from 'app/hooks';

export const LogoutButton = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const userData = useAppSelector(state => state.user.authData);

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userInfo');
    dispatch(userActions.clear());

    navigate('/auth');
  };

  return (
    <DropdownMenu
      renderSwitcher={({ onClick }) => (
        <Button view="action" onClick={onClick}>
          {userData?.username}
        </Button>
      )}
      items={[
        {
          text: 'Выйти',
          theme: 'danger',
          iconStart: <Icon size={16} data={ArrowRightFromSquare} />,
          action: logout,
        },
      ]}
    />
  );
};
