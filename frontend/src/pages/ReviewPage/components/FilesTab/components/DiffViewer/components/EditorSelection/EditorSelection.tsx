import { type editor as IEditor } from 'monaco-editor';
import { type ReactNode, useEffect } from 'react';

interface EditorSelectionProps {
  editor: IEditor.ICodeEditor;
  className: string;
  startLineNumber: number;
  startColumn: number;
  endLineNumber: number;
  endColumn: number;
  children?: ReactNode;
}

export const EditorSelection = ({
  editor,
  children,
  className,
  startLineNumber,
  startColumn,
  endLineNumber,
  endColumn,
}: EditorSelectionProps) => {
  useEffect(() => {
    const selectionDecorator = editor.createDecorationsCollection([
      {
        range: { startLineNumber, startColumn, endLineNumber, endColumn },
        options: { inlineClassName: className },
      },
    ]);

    return () => selectionDecorator.clear();
  }, [className, editor, endColumn, endLineNumber, startColumn, startLineNumber]);

  return children;
};
