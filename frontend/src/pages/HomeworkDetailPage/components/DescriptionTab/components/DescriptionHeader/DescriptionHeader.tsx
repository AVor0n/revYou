import { Label, Text } from '@gravity-ui/uikit';
import { type Homework } from '@domains';
import styles from './DescriptionHeader.module.scss';

interface DescriptionHeaderProps {
  homeworkInfo: Homework;
}

export const DescriptionHeader = ({ homeworkInfo }: DescriptionHeaderProps) => (
  <div className={styles.header}>
    <div>
      {(!!homeworkInfo.topic || !!homeworkInfo.lecture?.name) && (
        <Text variant="header-1">
          {!!homeworkInfo.topic && `Домашнее задание по теме ${homeworkInfo.topic}`}
          {!!homeworkInfo.lecture?.name && `. Лекция ${homeworkInfo.lecture.name}`}
        </Text>
      )}
    </div>
    <div className={styles.departmentsWrapper}>
      {homeworkInfo.departments?.map(dep => (
        <Label className={styles.dep} theme="info" key={dep}>
          {dep}
        </Label>
      ))}
    </div>
  </div>
);
