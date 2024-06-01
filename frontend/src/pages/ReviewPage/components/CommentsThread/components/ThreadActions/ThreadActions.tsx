import { TextArea, Button } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { useState } from 'react';
import { type CommentsThreadStatusEnum } from '@domains';
import { addComment, changeThreadStatus, useAppDispatch } from 'app';
import styles from './ThreadActions.module.scss';

interface ThreadActionsProps {
  threadId: number;
  threadStatus: CommentsThreadStatusEnum;
}

export const ThreadActions = ({ threadId, threadStatus }: ThreadActionsProps) => {
  const dispatch = useAppDispatch();
  const [newComment, setNewComment] = useState('');

  const onReply = () => {
    dispatch(addComment({ threadId, comment: { content: newComment } }));
    setNewComment('');
  };

  const onChangeStatus = () => {
    dispatch(changeThreadStatus([threadId, { status: threadStatus === 'ACTIVE' ? 'RESOLVED' : 'ACTIVE' }]));
    setNewComment('');
  };

  return (
    <div className={clsx(styles.threadActions, threadStatus === 'RESOLVED' && styles.resolved)}>
      <TextArea
        value={newComment}
        onChange={event => setNewComment(event.target.value)}
        placeholder="Ваш комментарий..."
      />
      <Button view="action" onClick={onReply} disabled={!newComment}>
        Ответить
      </Button>

      <Button view="outlined-action" onClick={onChangeStatus}>
        {threadStatus === 'ACTIVE' ? 'Завершить' : 'Возобновить'}
      </Button>
    </div>
  );
};
