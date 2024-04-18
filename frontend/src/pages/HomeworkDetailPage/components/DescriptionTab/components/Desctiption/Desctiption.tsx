import { Text } from '@gravity-ui/uikit';
import { type Homework } from '@domains';
import styles from './Desctiption.module.scss';

interface DesctiptionProps {
  homeworkInfo: Homework;
}

export const Desctiption = ({ homeworkInfo }: DesctiptionProps) => (
  <div>
    <Text variant="subheader-2">Описание</Text>
    <div className={styles.content}>
      <Text variant="body-2" className={styles.text}>
        {homeworkInfo.description}
      </Text>
    </div>
  </div>
);
