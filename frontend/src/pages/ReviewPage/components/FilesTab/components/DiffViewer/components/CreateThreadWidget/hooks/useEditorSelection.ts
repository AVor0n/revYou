import { editor as IEditor, type Selection } from 'monaco-editor';
import { useCallback, useEffect, useRef } from 'react';

interface UseEditorSelectionOptions {
  editor: IEditor.ICodeEditor;
  onSelection: (selection: Selection) => void;
}

export const useEditorSelection = ({ editor, onSelection }: UseEditorSelectionOptions) => {
  const tooltipNodeRef = useRef(document.createElement('div'));
  const widgetRef = useRef<IEditor.IContentWidget | null>(null);
  const selectionRef = useRef<Selection | null>(null);

  const clear = useCallback(() => {
    if (widgetRef.current) {
      editor.removeContentWidget(widgetRef.current);
      widgetRef.current = null;
      selectionRef.current = null;
    }
  }, [editor]);

  useEffect(() => {
    editor.onMouseDown(event => {
      if (tooltipNodeRef.current.contains(event.target.element)) {
        const selection = selectionRef.current;
        if (!selection) return;
        onSelection(selection);
      }

      clear();
    });

    editor.onMouseUp(() => {
      const selection = selectionRef.current;
      if (!selection || selection.isEmpty()) return;

      const widget: IEditor.IContentWidget = {
        getDomNode: () => tooltipNodeRef.current,
        getPosition: () => ({
          position: {
            column: selection.endColumn,
            lineNumber: selection.endLineNumber,
          },
          preference: [IEditor.ContentWidgetPositionPreference.BELOW, IEditor.ContentWidgetPositionPreference.ABOVE],
        }),
        getId: () => 'createCommentThreadFromSelection',
      };

      widgetRef.current = widget;
      editor.addContentWidget(widgetRef.current);
    });

    editor.onDidChangeCursorSelection(event => {
      selectionRef.current = event.selection;
    });
  }, [clear, editor, onSelection]);

  return {
    tooltipNode: tooltipNodeRef.current,
  };
};
