import { Card } from '@gravity-ui/uikit';
import { type CommentsThread as ICommentsThread } from '@domains';
import { addComment, resolveThread, useAppDispatch } from 'app';
import { ThreadActions, Comment } from './components';
import styles from './CommentsThread.module.scss';

interface CommentsThreadProps {
  thread: ICommentsThread;
}

export const CommentsThread = ({ thread }: CommentsThreadProps) => {
  const dispatch = useAppDispatch();

  const onReply = (content: string) => {
    dispatch(addComment({ threadId: thread.threadId, comment: { content } }));
  };

  const onResolve = () => {
    dispatch(resolveThread(thread.threadId));
  };

  return (
    <Card className={styles.thread} view={thread.status === 'ACTIVE' ? 'raised' : 'filled'}>
      <div className={styles.commentsContainer}>
        {thread.comments.map(comment => (
          <Comment comment={comment} key={comment.commentId} threadStatus={thread.status} />
        ))}
      </div>
      <ThreadActions threadStatus={thread.status} onReply={onReply} onResolve={onResolve} />
    </Card>
  );
};
