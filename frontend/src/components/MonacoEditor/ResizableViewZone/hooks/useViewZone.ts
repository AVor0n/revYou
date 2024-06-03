import { type editor as IEditor } from 'monaco-editor';
import { useCallback, useRef, useState } from 'react';

interface UseViewZone {
  id: string;
  editor: IEditor.ICodeEditor;
  afterLine: number;
}

export const useViewZone = ({ id, editor, afterLine }: UseViewZone) => {
  const overlayWidget = useRef<IEditor.IOverlayWidget | null>(null);
  const domNodeViewZone = useRef<HTMLDivElement>(document.createElement('div'));
  const domNodeOverlay = useRef<HTMLDivElement>(document.createElement('div'));
  const viewZoneId = useRef<string | null>(null);
  const [viewZoneHeight, setViewZoneHeight] = useState(0);

  const create = useCallback(() => {
    editor.changeViewZones(changeAccessor => {
      const zoneOptions: IEditor.IViewZone = {
        afterLineNumber: afterLine,
        domNode: domNodeViewZone.current,
        showInHiddenAreas: true,
        get heightInPx() {
          return viewZoneHeight;
        },
        onDomNodeTop: top => {
          domNodeOverlay.current.style.top = `${top}px`;
        },
        onComputedHeight: h => {
          domNodeOverlay.current.style.height = `${h}px`;
        },
      };

      overlayWidget.current = {
        getId: () => id,
        getDomNode: () => domNodeOverlay.current,
        getPosition: () => null,
      };

      editor.addOverlayWidget(overlayWidget.current);
      viewZoneId.current = changeAccessor.addZone(zoneOptions);
    });
  }, [editor, afterLine, viewZoneHeight, id]);

  const update = useCallback((newHeight?: number) => {
    if (newHeight === undefined) return;
    setViewZoneHeight(newHeight);

    const focusedNode = document.activeElement as HTMLElement;
    setTimeout(() => focusedNode.focus());
  }, []);

  const clear = useCallback(() => {
    editor.changeViewZones(changeAccessor => {
      if (viewZoneId.current) {
        changeAccessor.removeZone(viewZoneId.current);
      }
    });

    if (overlayWidget.current) {
      editor.removeOverlayWidget(overlayWidget.current);
    }
  }, [editor]);

  return {
    domNode: domNodeOverlay.current,
    create,
    update,
    clear,
  };
};
