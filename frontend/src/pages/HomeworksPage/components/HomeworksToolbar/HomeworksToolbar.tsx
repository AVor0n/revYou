import { Magnifier, Plus } from '@gravity-ui/icons';
import { Button, Icon, TextInput } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { getUserRole } from 'app';
import styles from './HomeworksToolbar.module.scss';

interface HomeworksToolbarProps {
  disabled?: boolean;
  search: string;
  onSearch: (search: string) => void;
  onCreate: () => void;
}

export const HomeworksToolbar = ({ search, onSearch, onCreate, disabled }: HomeworksToolbarProps) => {
  const role = useSelector(getUserRole);

  return (
    <div className={styles.toolbar}>
      <TextInput
        className={styles.search}
        value={search}
        onChange={event => onSearch(event.target.value)}
        label="Поиск"
        startContent={<Icon data={Magnifier} />}
        disabled={disabled}
      />
      {role === 'TEACHER' && (
        <Button view="action" onClick={onCreate}>
          <Icon data={Plus} />
          Создать
        </Button>
      )}
    </div>
  );
};
