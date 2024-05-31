import { dateTimeParse } from '@gravity-ui/date-utils';
import { Person } from '@gravity-ui/icons';
import { Avatar, Tooltip, Label, Text } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { type CommentsThreadStatusEnum, type Comment as IComment } from '@domains';
import styles from './Comment.module.scss';

interface CommentProps {
  comment: IComment;
  threadStatus: CommentsThreadStatusEnum;
}

export const Comment = ({ comment, threadStatus }: CommentProps) => (
  <div className={clsx(styles.comment, threadStatus === 'RESOLVED' && styles.resolved)}>
    <div className={styles.commentInfo}>
      <Avatar icon={Person} size="xs" />
      <Tooltip openDelay={500} content="Создан">
        <Label>
          <Text variant="caption-2">{dateTimeParse(comment.createdAt)?.format('DD.MM.YYYY HH:MM:ss')}</Text>
        </Label>
      </Tooltip>
    </div>
    <p>{comment.content}</p>
  </div>
);
