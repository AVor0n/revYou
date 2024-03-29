import { Magnifier } from '@gravity-ui/icons';
import { Icon, TextInput } from '@gravity-ui/uikit';
import styles from './HomeworksToolbar.module.scss';

interface HomeworksToolbarProps {
  search: string;
  onSearch: (search: string) => void;
}

export const HomeworksToolbar = ({ search, onSearch }: HomeworksToolbarProps) => (
  <div className={styles.toolbar}>
    <TextInput
      className={styles.search}
      value={search}
      onChange={event => onSearch(event.target.value)}
      label="Поиск"
      startContent={<Icon data={Magnifier} />}
    />
  </div>
);
