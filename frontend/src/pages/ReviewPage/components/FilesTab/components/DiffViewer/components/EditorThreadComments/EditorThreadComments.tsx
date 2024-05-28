import { type editor as IEditor } from 'monaco-editor';
import { CommentsThread } from '@pages/ReviewPage/components/CommentsThread';
import { EditorSelection } from '../EditorSelection';
import { ResizableViewZone } from '../ResizableViewZone';
import type { CommentsThread as ICommentsThread } from '@domains';
import styles from './EditorThreadComments.module.scss';

interface EditorThreadCommentsProps {
  editor: IEditor.ICodeEditor;
  threads: ICommentsThread[];
}

export const EditorThreadComments = ({ editor, threads }: EditorThreadCommentsProps) =>
  threads.map(thread => (
    <ResizableViewZone editor={editor} afterLine={thread.endLine} key={thread.threadId}>
      <EditorSelection
        editor={editor}
        className={styles.commentSelection}
        startLineNumber={thread.startLine}
        startColumn={thread.startSymbol}
        endLineNumber={thread.endLine}
        endColumn={thread.endSymbol}
      />
      <div className={styles.commentsThreadWrapper}>
        <CommentsThread thread={thread} />
      </div>
    </ResizableViewZone>
  ));
