import { TextArea, Button } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { useState } from 'react';
import { useAddCommentMutation, useResolveCommentsThreadMutation, type CommentsThreadStatus } from '@api';
import { useApiError } from '@shared/utils';
import styles from './ThreadActions.module.scss';

interface ThreadActionsProps {
  threadId: number;
  threadStatus: CommentsThreadStatus;
}

export const ThreadActions = ({ threadId, threadStatus }: ThreadActionsProps) => {
  const [addComment, { isLoading: addCommentLoading, error: addCommentError }] = useAddCommentMutation();
  const [changeThreadStatus, { isLoading: changeThreadLoading, error: changeThreadError }] =
    useResolveCommentsThreadMutation();

  useApiError(addCommentError, { name: 'addComment', title: 'Не удалось создать комментарий' });
  useApiError(changeThreadError, { name: 'changeThread', title: 'Не удалось изменить состояние' });

  const [newComment, setNewComment] = useState('');

  const onReply = () => {
    addComment({ threadId, commentPostDto: { content: newComment } });
    setNewComment('');
  };

  const onChangeStatus = () => {
    changeThreadStatus({
      threadId,
      commentsThreadResolveDto: { status: threadStatus === 'ACTIVE' ? 'RESOLVED' : 'ACTIVE' },
    });
    setNewComment('');
  };

  return (
    <div className={clsx(styles.threadActions, threadStatus === 'RESOLVED' && styles.resolved)}>
      <TextArea
        value={newComment}
        onChange={event => setNewComment(event.target.value)}
        placeholder="Ваш комментарий..."
        disabled={addCommentLoading || changeThreadLoading}
      />
      <Button view="action" onClick={onReply} disabled={!newComment || changeThreadLoading} loading={addCommentLoading}>
        Ответить
      </Button>

      <Button
        view="outlined-action"
        onClick={onChangeStatus}
        disabled={addCommentLoading}
        loading={changeThreadLoading}
      >
        {threadStatus === 'ACTIVE' ? 'Завершить' : 'Возобновить'}
      </Button>
    </div>
  );
};
