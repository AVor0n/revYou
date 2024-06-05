import { useCallback, useEffect } from 'react';
import { loadFile, reviewActions, useAppDispatch, type FileNode } from 'app';
import { useAppSelector } from 'app/hooks';

export interface UseFileDiffContent {
  sourceSha: string;
  targetSha: string;
  file: FileNode;
}

export const useFileDiffContent = ({ file, sourceSha, targetSha }: UseFileDiffContent) => {
  const dispatch = useAppDispatch();
  const { filesCache } = useAppSelector(state => state.review);

  const sourceFile = filesCache[file.oldPath]?.[sourceSha];
  const targetFile = filesCache[file.path]?.[targetSha];

  const loadFileByRef = useCallback(
    (filePath: string, ref: string) => {
      dispatch(loadFile({ filePath, ref }));
      dispatch(reviewActions.addRequestInProgress(`loadFile/${filePath}/${ref}`));
    },
    [dispatch],
  );

  useEffect(() => {
    if (file.status === 'deleted') {
      dispatch(reviewActions.addFileContent({ path: file.path, ref: targetSha, content: null }));
    } else {
      loadFileByRef(file.path, targetSha);
    }

    if (file.status === 'added') {
      dispatch(reviewActions.addFileContent({ path: file.oldPath, ref: sourceSha, content: null }));
    } else {
      loadFileByRef(file.oldPath, sourceSha);
    }
  }, [dispatch, file, loadFileByRef, sourceSha, targetSha]);

  return {
    sourceFile,
    targetFile,
  };
};
