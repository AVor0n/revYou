import { Card } from '@gravity-ui/uikit';
import { type CommentsThread as ICommentsThread } from '@domains';
import { ThreadActions, Comment } from './components';
import styles from './CommentsThread.module.scss';

interface CommentsThreadProps {
  thread: ICommentsThread;
}

export const CommentsThread = ({ thread }: CommentsThreadProps) => (
  <Card className={styles.thread} view={thread.status === 'ACTIVE' ? 'raised' : 'filled'}>
    <div className={styles.commentsContainer}>
      {thread.comments.map(comment => (
        <Comment comment={comment} key={comment.commentId} threadStatus={thread.status} />
      ))}
    </div>
    <ThreadActions threadId={thread.threadId} threadStatus={thread.status} />
  </Card>
);
