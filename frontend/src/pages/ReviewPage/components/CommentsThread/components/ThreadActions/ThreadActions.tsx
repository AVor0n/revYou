import { TextArea, Button } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { useState } from 'react';
import { type CommentsThreadStatus } from '@domains';
import { addComment, changeThreadStatus, useAppDispatch } from 'app';
import styles from './ThreadActions.module.scss';

interface ThreadActionsProps {
  threadId: number;
  threadStatus: CommentsThreadStatus;
}

export const ThreadActions = ({ threadId, threadStatus }: ThreadActionsProps) => {
  const dispatch = useAppDispatch();
  const [newComment, setNewComment] = useState('');
  const [loading, setLoading] = useState(false);

  const onReply = async () => {
    setLoading(true);
    await dispatch(addComment({ threadId, comment: { content: newComment } }));
    setLoading(false);
    setNewComment('');
  };

  const onChangeStatus = async () => {
    setLoading(true);
    await dispatch(changeThreadStatus([threadId, { status: threadStatus === 'ACTIVE' ? 'RESOLVED' : 'ACTIVE' }]));
    setLoading(false);
    setNewComment('');
  };

  return (
    <div className={clsx(styles.threadActions, threadStatus === 'RESOLVED' && styles.resolved)}>
      <TextArea
        value={newComment}
        onChange={event => setNewComment(event.target.value)}
        placeholder="Ваш комментарий..."
        disabled={loading}
      />
      <Button view="action" onClick={onReply} disabled={!newComment} loading={loading}>
        Ответить
      </Button>

      <Button view="outlined-action" onClick={onChangeStatus} loading={loading}>
        {threadStatus === 'ACTIVE' ? 'Завершить' : 'Возобновить'}
      </Button>
    </div>
  );
};
