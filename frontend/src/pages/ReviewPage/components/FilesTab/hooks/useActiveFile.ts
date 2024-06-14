import { useCallback, useMemo } from 'react';
import { useSearchParams } from 'react-router-dom';
import { isFile, type FilesTreeItem } from '@shared/types';
import { findInTree } from '@ui';
import { useAppSelector } from 'app/hooks';

export const useActiveFile = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const { filesTree } = useAppSelector(state => state.review);
  const activeFile = useMemo(() => {
    const path = searchParams.get('path');
    if (!filesTree || !path) return null;
    const file = findInTree(filesTree, item => item.path === path);
    return file && isFile(file) ? file : null;
  }, [filesTree, searchParams]);

  const setActiveFile = useCallback(
    (file: FilesTreeItem | null) => {
      setSearchParams(params => {
        if (file) {
          params.set('path', file.path);
        } else {
          params.delete('path');
        }
        return params;
      });
    },
    [setSearchParams],
  );

  return {
    activeFile,
    setActiveFile,
  };
};
