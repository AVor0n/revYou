import { type editor as IEditor } from 'monaco-editor';
import { useCallback, useRef } from 'react';

export interface ViewZoneData {
  afterLine: number;
}

export interface ViewZone extends IEditor.IViewZone {
  id: string;
}

interface UseViewZone {
  editor: IEditor.ICodeEditor;
  afterLine: number;
}

export interface ViewZoneNodeInfo<T> {
  node: HTMLDivElement;
  data: T;
  viewModel: ViewZone;
}

export const useViewZone = ({ editor, afterLine }: UseViewZone) => {
  const domNode = useRef<HTMLDivElement>(document.createElement('div'));
  const viewZone = useRef<ViewZone | null>(null);

  const create = useCallback(() => {
    editor.changeViewZones(changeAccessor => {
      const zoneOptions: IEditor.IViewZone = {
        afterLineNumber: afterLine,
        domNode: domNode.current,
        showInHiddenAreas: true,
      };

      viewZone.current = {
        id: changeAccessor.addZone(zoneOptions),
        ...zoneOptions,
      };
    });
  }, [editor, afterLine]);

  const update = useCallback(
    (height?: number) => {
      const viewZoneCurrent = viewZone.current;
      if (height === undefined || !viewZoneCurrent) return;

      editor.changeViewZones(changeAccessor => {
        const { id, ...options } = viewZoneCurrent;
        changeAccessor.removeZone(id);
        const newViewZone = { ...options, heightInPx: height };
        const newViewZoneId = changeAccessor.addZone(newViewZone);

        viewZone.current = {
          id: newViewZoneId,
          ...newViewZone,
        };
      });
    },
    [editor],
  );

  const clear = useCallback(() => {
    editor.changeViewZones(changeAccessor => {
      if (viewZone.current) {
        changeAccessor.removeZone(viewZone.current.id);
      }
    });
  }, [editor]);

  return {
    domNode: domNode.current,
    create,
    update,
    clear,
  };
};
