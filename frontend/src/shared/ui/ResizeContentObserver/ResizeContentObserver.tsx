import { useCallback, useRef, type ReactNode } from 'react';
import useResizeObserver, {
  type ObservedSize,
  type ResizeHandler,
  type ResizeObserverBoxOptions,
} from 'use-resize-observer';

interface ResizeContentObserverProps {
  children: ReactNode;
  onResize: ResizeHandler;
  box?: ResizeObserverBoxOptions;
  dimension?: 'width' | 'height' | 'both';
}

export const ResizeContentObserver = ({ children, dimension = 'both', ...options }: ResizeContentObserverProps) => {
  const sizeRef = useRef<ObservedSize | null>(null);
  const onResize = useCallback(
    (size: ObservedSize) => {
      if (dimension === 'height' && size.height === sizeRef.current?.height) {
        return;
      }
      if (dimension === 'width' && size.width === sizeRef.current?.width) {
        return;
      }

      sizeRef.current = size;
      options.onResize(size);
    },
    [dimension, options],
  );

  const { ref } = useResizeObserver<HTMLDivElement>({
    ...options,
    onResize,
  });

  return <div ref={ref}>{children}</div>;
};
