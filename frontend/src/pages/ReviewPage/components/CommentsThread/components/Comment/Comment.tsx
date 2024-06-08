import { dateTimeParse } from '@gravity-ui/date-utils';
import { FaceAlien, GraduationCap } from '@gravity-ui/icons';
import { Avatar, Tooltip, Text } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { useCallback } from 'react';
import { useSelector } from 'react-redux';
import { IntervalRerender } from '@components/IntervalRerender';
import { type CommentsThreadStatus, type Comment as IComment } from '@domains';
import { getAuthData } from 'app';
import { dateRelativeNow } from 'utils';
import styles from './Comment.module.scss';

interface CommentProps {
  comment: IComment;
  threadStatus: CommentsThreadStatus;
}

export const Comment = ({ comment, threadStatus }: CommentProps) => {
  const authInfo = useSelector(getAuthData);
  const isMyComment = authInfo?.userId === comment.authorId;

  const commentContainerClasses = clsx(
    styles.comment,
    threadStatus === 'RESOLVED' && styles.resolved,
    isMyComment && styles.myComment,
  );

  const refreshTime = useCallback(() => dateRelativeNow(new Date(comment.createdAt)), [comment.createdAt]);

  return (
    <div className={commentContainerClasses}>
      <div className={styles.commentInfo}>
        {isMyComment ? (
          <Tooltip content="Мой комментарий">
            <Avatar icon={GraduationCap} size="xs" color="RoyalBlue" />
          </Tooltip>
        ) : (
          <Tooltip content="Комментарий ревьюера">
            <Avatar icon={FaceAlien} size="xs" color="ForestGreen" />
          </Tooltip>
        )}

        <Tooltip content={`Создан ${dateTimeParse(comment.createdAt)?.format('DD MMMM YYYY в HH:MM:ss')}`}>
          <Text variant="caption-2" color="hint">
            <IntervalRerender getContent={refreshTime} intervalInMs={60000} />
          </Text>
        </Tooltip>
      </div>

      <p>{comment.content}</p>
    </div>
  );
};
