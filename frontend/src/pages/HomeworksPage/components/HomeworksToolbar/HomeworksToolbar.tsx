import { Magnifier, Plus } from '@gravity-ui/icons';
import { Button, Icon, TextInput } from '@gravity-ui/uikit';
import styles from './HomeworksToolbar.module.scss';

interface HomeworksToolbarProps {
  disabled?: boolean;
  search: string;
  onSearch: (search: string) => void;
  onCreate: () => void;
}

export const HomeworksToolbar = ({ search, onSearch, onCreate, disabled }: HomeworksToolbarProps) => (
  <div className={styles.toolbar}>
    <TextInput
      className={styles.search}
      value={search}
      onChange={event => onSearch(event.target.value)}
      label="Поиск"
      startContent={<Icon data={Magnifier} />}
      disabled={disabled}
    />
    <Button view="action" onClick={onCreate}>
      <Icon data={Plus} />
      Создать
    </Button>
  </div>
);
