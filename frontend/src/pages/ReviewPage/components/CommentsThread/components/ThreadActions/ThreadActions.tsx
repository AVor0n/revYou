import { TextArea, Button } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { useState } from 'react';
import { type CommentsThread } from '@domains';
import styles from './ThreadActions.module.scss';

interface ThreadActionsProps {
  threadStatus: CommentsThread['status'];
  onReply: (text: string) => void;
  onResolve: () => void;
}

export const ThreadActions = ({ threadStatus, ...props }: ThreadActionsProps) => {
  const [newComment, setNewComment] = useState('');

  const onReply = () => {
    props.onReply(newComment);
    setNewComment('');
  };

  const onResolve = () => {
    props.onResolve();
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

      <Button view="outlined-action" onClick={onResolve} disabled={threadStatus === 'RESOLVED'}>
        Завершить
      </Button>
    </div>
  );
};
