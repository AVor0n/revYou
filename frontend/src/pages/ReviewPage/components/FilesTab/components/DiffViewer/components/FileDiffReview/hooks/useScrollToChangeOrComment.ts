import { type editor } from 'monaco-editor';
import { useEffect, useMemo } from 'react';
import type { CommentsThread } from '@api';

export interface UseScrollToChangeOrComment {
  editor?: editor.IDiffEditor;
  threads?: CommentsThread[];
}

export const useScrollToChangeOrComment = ({ editor, threads }: UseScrollToChangeOrComment) => {
  const firstThreadStartLine = useMemo(() => {
    if (!editor || !threads) return 0;

    return threads.reduce((lineNumber, thread) => (lineNumber < thread.startLine ? thread.startLine : lineNumber), 0);
  }, [threads, editor]);

  useEffect(() => {
    if (!editor) return;
    const firstDiffStartLine = editor.getLineChanges()?.[0]?.modifiedStartLineNumber ?? 0;

    editor.revealLineNearTop(Math.max(firstDiffStartLine, firstThreadStartLine));
  }, [editor, firstThreadStartLine]);
};
