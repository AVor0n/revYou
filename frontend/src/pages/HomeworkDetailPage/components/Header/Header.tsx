import { Breadcrumbs, FirstDisplayedItemsCount, LastDisplayedItemsCount } from '@gravity-ui/uikit';
import { useNavigate } from 'react-router-dom';
import { homeworkActions, useAppDispatch } from 'app';
import styles from './Header.module.scss';

interface HeaderProps {
  homeworkName: string;
}

export const Header = ({ homeworkName }: HeaderProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const goToBack = () => {
    navigate('/homeworks');
    dispatch(homeworkActions.setSelectedHomework(null));
  };

  return (
    <div className={styles.header}>
      <Breadcrumbs
        items={[
          {
            text: 'Домашки',
            action: goToBack,
          },
          {
            text: homeworkName,
            action: () => {},
          },
        ]}
        firstDisplayedItemsCount={FirstDisplayedItemsCount.Zero}
        lastDisplayedItemsCount={LastDisplayedItemsCount.Two}
      />
    </div>
  );
};
