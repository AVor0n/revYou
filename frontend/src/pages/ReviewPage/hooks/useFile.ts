import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { getFilesCache, loadFile, reviewActions, useAppDispatch } from 'app';

export interface UseFile {
  filePath: string;
  ref: string;
}

export const useFile = ({ filePath, ref }: UseFile) => {
  const dispatch = useAppDispatch();
  const cache = useSelector(getFilesCache);

  const content = cache[filePath]?.[ref];

  useEffect(() => {
    if (content === undefined) {
      dispatch(loadFile({ filePath, ref }));
      dispatch(reviewActions.addRequestInProgress(`loadFile/${filePath}/${ref}`));
    }
  }, [content, dispatch, filePath, ref]);

  return content;
};
