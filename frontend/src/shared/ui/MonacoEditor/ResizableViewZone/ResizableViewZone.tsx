import { type editor as IEditor } from 'monaco-editor';
import { type ReactNode, useEffect, useRef } from 'react';
import { createPortal } from 'react-dom';
import useResizeObserver, { type ObservedSize } from 'use-resize-observer';
import { ResizeContentObserver } from '../../ResizeContentObserver';
import { useViewZone } from './hooks';

interface ResizableViewZoneProps {
  id: string;
  editor: IEditor.ICodeEditor;
  afterLine: number;
  children: ReactNode;
}

export const ResizableViewZone = ({ id, editor, afterLine, children }: ResizableViewZoneProps) => {
  const editorDomNode = useRef(editor.getDomNode());
  const { domNode, create, update, clear } = useViewZone({ id, editor, afterLine });
  const { contentWidth, contentLeft } = editor.getLayoutInfo();

  const onResizeViewZoneContent = (size: ObservedSize) => update(size.height);

  useResizeObserver({ ref: editorDomNode });

  useEffect(() => {
    create();
    return () => clear();
  }, [clear, create]);

  return createPortal(
    <ResizeContentObserver dimension="height" onResize={onResizeViewZoneContent}>
      <div style={{ marginLeft: contentLeft, width: contentWidth }}>{children}</div>
    </ResizeContentObserver>,
    domNode,
  );
};
