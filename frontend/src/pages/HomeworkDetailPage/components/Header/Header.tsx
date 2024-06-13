import { Breadcrumbs, FirstDisplayedItemsCount, LastDisplayedItemsCount } from '@gravity-ui/uikit';
import { useNavigate } from 'react-router-dom';
import styles from './Header.module.scss';

interface HeaderProps {
  homeworkName: string;
}

export const Header = ({ homeworkName }: HeaderProps) => {
  const navigate = useNavigate();

  const goToBack = () => {
    navigate('/homeworks');
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
