import { type editor as IEditor } from 'monaco-editor';
import { EditorSelection, ResizableViewZone } from '@ui';
import { CommentsThread } from '../../../../../../../CommentsThread';
import type { CommentsThread as ICommentsThread } from '@api';
import styles from './EditorThreadComments.module.scss';

interface EditorThreadCommentsProps {
  editor: IEditor.ICodeEditor;
  threads: ICommentsThread[];
}

export const EditorThreadComments = ({ editor, threads }: EditorThreadCommentsProps) =>
  threads.map(thread => (
    <ResizableViewZone id={String(thread.threadId)} editor={editor} afterLine={thread.endLine} key={thread.threadId}>
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
