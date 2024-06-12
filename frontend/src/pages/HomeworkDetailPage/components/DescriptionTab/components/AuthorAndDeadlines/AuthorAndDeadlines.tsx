import { dateTimeParse } from '@gravity-ui/date-utils';
import { GraduationCap } from '@gravity-ui/icons';
import { Label, Text, Tooltip, UserLabel } from '@gravity-ui/uikit';
import { type Homework } from '@api';
import styles from './AuthorAndDeadlines.module.scss';

interface AuthorAndDeadlinesProps {
  homeworkInfo: Homework;
}

export const AuthorAndDeadlines = ({ homeworkInfo }: AuthorAndDeadlinesProps) => (
  <div className={styles.AuthorAndDeadlines}>
    <Text variant="subheader-2">Кому и когда сдавать</Text>
    <div className={styles.content}>
      <div className={styles.contentItem}>
        Автор
        <UserLabel type="person" view="outlined" avatar={{ icon: GraduationCap }}>
          {homeworkInfo.author.firstName} {homeworkInfo.author.lastName}
        </UserLabel>
      </div>
      <div className={styles.contentItem}>
        Дедлайны
        <Label theme="success" size="m" className={styles.date}>
          {dateTimeParse(homeworkInfo.startDate)?.format('DD.MM.YYYY')}
        </Label>
        -
        <Label theme="warning" size="m" className={styles.date}>
          {dateTimeParse(homeworkInfo.completionDeadline)?.format('DD.MM.YYYY')}
        </Label>
        <Tooltip openDelay={0} content="Время на ревью">
          <Label theme="info" className={styles.reviewDuration}>
            <Text variant="caption-2">{homeworkInfo.reviewDuration}ч</Text>
          </Label>
        </Tooltip>
      </div>
    </div>
  </div>
);
