import { Button, Card } from '@gravity-ui/uikit';
import { type editor as IEditor, type Selection } from 'monaco-editor';
import { useState } from 'react';
import { createPortal } from 'react-dom';
import { useSelector } from 'react-redux';
import { createThread, getActiveFilePath, getCreateThreadInProgress, useAppDispatch } from 'app';
import { EditorSelection } from '../EditorSelection';
import { ResizableViewZone } from '../ResizableViewZone';
import { CreateThreadCard } from './components';
import { useEditorSelection } from './hooks';
import styles from './CreateThreadWidget.module.scss';

interface CreateThreadWidgetProps {
  editor: IEditor.ICodeEditor;
  reviewId: number;
  commitSha: string;
}

export const CreateThreadWidget = ({ editor, reviewId, commitSha }: CreateThreadWidgetProps) => {
  const dispatch = useAppDispatch();
  const createInProgress = useSelector(getCreateThreadInProgress);
  const filePath = useSelector(getActiveFilePath);
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
        <ResizableViewZone editor={editor} afterLine={selection.endLineNumber}>
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
              isLoading={createInProgress}
            />
          </div>
        </ResizableViewZone>
      )}
    </>
  );
};
