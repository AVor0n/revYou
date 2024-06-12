import { Button, Card } from '@gravity-ui/uikit';
import { type editor as IEditor, type Selection } from 'monaco-editor';
import { useState } from 'react';
import { createPortal } from 'react-dom';
import { useSelector } from 'react-redux';
import { EditorSelection, ResizableViewZone } from '@ui';
import { createThread, getRequestInProgress, useAppDispatch } from 'app';
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
  const dispatch = useAppDispatch();
  const requestInProgress = useSelector(getRequestInProgress);
  const [selection, setSelection] = useState<Selection | null>(null);
  const { tooltipNode } = useEditorSelection({ editor, onSelection: setSelection });

  const onCreateThread = (content: string) => {
    if (!selection) {
      throw new Error('Not selection');
    }

    dispatch(
      createThread({
        commitSha,
        content,
        filePath,
        reviewId,
        startLine: selection.startLineNumber,
        startSymbol: selection.startColumn,
        endLine: selection.endLineNumber,
        endSymbol: selection.endColumn,
      }),
    ).then(() => {
      setSelection(null);
    });
  };

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
            <CreateThreadCard
              onCreate={onCreateThread}
              onCancel={() => setSelection(null)}
              isLoading={requestInProgress.createThread}
            />
          </div>
        </ResizableViewZone>
      )}
    </>
  );
};
