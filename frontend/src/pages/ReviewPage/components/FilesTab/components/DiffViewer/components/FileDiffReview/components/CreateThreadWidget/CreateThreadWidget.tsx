import { Button, Card } from '@gravity-ui/uikit';
import { type editor as IEditor, type Selection } from 'monaco-editor';
import { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import { useStartThreadMutation } from '@shared/api';
import { EditorSelection, ResizableViewZone } from '@ui';
import { CreateThreadCard } from './components';
import { useEditorSelection } from './hooks';
import styles from './CreateThreadWidget.module.scss';

interface CreateThreadWidgetProps {
  editor: IEditor.ICodeEditor;
  reviewId: number;
  commitSha: string;
  filePath: string;
}

export const CreateThreadWidget = ({ editor, reviewId, commitSha, filePath }: CreateThreadWidgetProps) => {
  const [selection, setSelection] = useState<Selection | null>(null);
  const { tooltipNode } = useEditorSelection({ editor, onSelection: setSelection });
  const [createThread, { isSuccess, isLoading }] = useStartThreadMutation();

  const onCreateThread = (content: string) => {
    if (!selection) {
      throw new Error('Not selection');
    }

    createThread({
      threadPost: {
        commitSha,
        content,
        filePath,
        reviewId,
        startLine: selection.startLineNumber,
        startSymbol: selection.startColumn,
        endLine: selection.endLineNumber,
        endSymbol: selection.endColumn,
      },
    });
  };

  useEffect(() => {
    if (isSuccess) setSelection(null);
  }, [isSuccess]);

  return (
    <>
      {!selection &&
        createPortal(
          <Card view="raised" className={styles.commentCardWrapper}>
            <Button view="outlined-action">Комментировать</Button>
          </Card>,
          tooltipNode,
        )}

      {selection && (
        <ResizableViewZone id={commitSha} editor={editor} afterLine={selection.endLineNumber}>
          <div className={styles.commentCardWrapper}>
            <EditorSelection
              editor={editor}
              className={styles.commentSelection}
              startLineNumber={selection.startLineNumber}
              startColumn={selection.startColumn}
              endLineNumber={selection.endLineNumber}
              endColumn={selection.endColumn}
            />
            <CreateThreadCard onCreate={onCreateThread} onCancel={() => setSelection(null)} isLoading={isLoading} />
          </div>
        </ResizableViewZone>
      )}
    </>
  );
};
