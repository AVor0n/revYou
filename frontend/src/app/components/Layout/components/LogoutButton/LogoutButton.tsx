import { ArrowRightFromSquare } from '@gravity-ui/icons';
import { Button, DropdownMenu, Icon } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getAuthData, homeworkActions, lectureActions, reviewActions, solutionActions, userActions } from 'app';
import { useAppDispatch } from 'app/hooks';

export const LogoutButton = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const userData = useSelector(getAuthData);

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userInfo');

    dispatch(homeworkActions.clear());
    dispatch(solutionActions.clear());
    dispatch(lectureActions.clear());
    dispatch(reviewActions.clear());
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
