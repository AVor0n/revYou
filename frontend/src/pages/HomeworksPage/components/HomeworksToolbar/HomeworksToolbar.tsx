import { Magnifier } from '@gravity-ui/icons';
import { Icon, TextInput } from '@gravity-ui/uikit';
import styles from './HomeworksToolbar.module.scss';

interface HomeworksToolbarProps {
  disabled?: boolean;
  search: string;
  onSearch: (search: string) => void;
}

export const HomeworksToolbar = ({ search, onSearch, disabled }: HomeworksToolbarProps) => (
  <div className={styles.toolbar}>
    <TextInput
      className={styles.search}
      value={search}
      onChange={event => onSearch(event.target.value)}
      label="Поиск"
      startContent={<Icon data={Magnifier} />}
      disabled={disabled}
    />
  </div>
);
