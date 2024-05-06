import { ArrowUturnCwLeft } from '@gravity-ui/icons';
import { Button, Icon, Text } from '@gravity-ui/uikit';
import { type PropsWithChildren } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getSelectedHomework, homeworkActions, useAppDispatch } from 'app';
import styles from './Header.module.scss';

export const Header = ({ children }: PropsWithChildren) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const selectedHomework = useSelector(getSelectedHomework);

  const gotToBack = () => {
    if (selectedHomework?.id) {
      navigate(`/homeworks/${selectedHomework.id}/solutions`);
    } else {
      navigate('/homeworks');
    }
    dispatch(homeworkActions.setSelectedHomework(null));
  };

  return (
    <div className={styles.header}>
      <Button onClick={gotToBack} view="flat-action">
        <Icon data={ArrowUturnCwLeft} />
        Вернуться
      </Button>

      <Text variant="header-2">{children}</Text>
    </div>
  );
};
