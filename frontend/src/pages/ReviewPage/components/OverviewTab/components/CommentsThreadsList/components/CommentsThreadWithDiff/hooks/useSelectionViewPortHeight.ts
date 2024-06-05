import { editor as IEditor } from 'monaco-editor';
import { useMemo } from 'react';

export interface UseRangeViewPortHeight {
  offsetInLines?: number;
  diffEditor?: IEditor.IDiffEditor;
  startLine?: number;
  sourceFile?: string | null;
  targetFile?: string | null;
  endLine?: number;
}

export const useRangeEditorHeight = ({
  diffEditor,
  startLine = 0,
  endLine = 0,
  sourceFile,
  targetFile,
  offsetInLines = 3,
}: UseRangeViewPortHeight) => {
  const fileLines = useMemo(
    () => Math.max(sourceFile?.split('\n').length ?? 0, targetFile?.split('\n').length ?? 0),
    [sourceFile, targetFile],
  );

  const lineHeight = diffEditor?.getModifiedEditor().getOption(IEditor.EditorOption.lineHeight) ?? 19;

  const heightInLines = Math.min(fileLines, endLine + offsetInLines) - Math.max(0, startLine - offsetInLines);

  return heightInLines * lineHeight;
};
