import { debounce } from 'lodash-es';
import { type editor as IEditor } from 'monaco-editor';
import { type ReactNode, useEffect, useRef, useState } from 'react';
import { createPortal } from 'react-dom';
import useResizeObserver, { type ObservedSize } from 'use-resize-observer';
import { ResizeContentObserver } from '@components/ResizeContentObserver';
import { useViewZone } from './hooks';

interface ResizableViewZoneProps {
  editor: IEditor.ICodeEditor;
  afterLine: number;
  children: ReactNode;
}

export const ResizableViewZone = ({ editor, afterLine, children }: ResizableViewZoneProps) => {
  const editorDomNode = useRef(editor.getDomNode());
  const [contentVisibleWidth, setContentVisibleWidth] = useState<number | undefined>(undefined);
  const { domNode, create, update, clear } = useViewZone({ editor, afterLine });

  const onResizeViewZoneContent = (size: ObservedSize) => update(size.height);

  const onResizeEditor = debounce(() => {
    setContentVisibleWidth(editor.getLayoutInfo().contentWidth);
  }, 100);

  useResizeObserver({
    ref: editorDomNode,
    onResize: onResizeEditor,
  });

  useEffect(() => {
    create();
    return () => clear();
  }, [clear, create]);

  return createPortal(
    <ResizeContentObserver dimension="height" onResize={onResizeViewZoneContent}>
      <div style={{ maxWidth: contentVisibleWidth }}>{children}</div>
    </ResizeContentObserver>,
    domNode,
  );
};
