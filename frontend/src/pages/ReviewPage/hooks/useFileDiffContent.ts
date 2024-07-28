import { useEffect } from 'react';
import { useLazyGetRawFileQuery, injectedRtkApi } from '@shared/api';
import { useApiError } from '@shared/utils';
import { useAppSelector } from 'app/hooks';
import type { FileNode } from '@shared/types';

export interface UseFileDiffContent {
  sourceSha: string;
  targetSha: string;
  file: FileNode;
}

export const useFileDiffContent = ({ file, sourceSha, targetSha }: UseFileDiffContent) => {
  const projectId = useAppSelector(state => state.review.reviewInfo?.projectId);

  const [loadSourceFile, { data: sourceFile = '', isLoading: sourceIsLoading, error: sourceError }] =
    useLazyGetRawFileQuery();
  useApiError(sourceError, { name: 'loadSource', title: 'Ошибка при получении старой версии файла' });

  const [loadTargetFile, { data: targetFile = '', isLoading: targetIsLoading, error: targetError }] =
    useLazyGetRawFileQuery();
  useApiError(targetError, { name: 'loadTarget', title: 'Ошибка при запросе новой версии файла' });

  useEffect(() => {
    if (projectId === undefined) throw new Error('not provided projectId');

    if (file.status === 'deleted') {
      injectedRtkApi.util.upsertQueryData('getRawFile', { projectId, filePath: file.path, ref: sourceSha }, '');
    } else {
      loadTargetFile({ projectId, filePath: file.path, ref: targetSha });
    }

    if (file.status === 'added') {
      injectedRtkApi.util.upsertQueryData('getRawFile', { projectId, filePath: file.oldPath, ref: sourceSha }, '');
    } else {
      loadSourceFile({ projectId, filePath: file.oldPath, ref: sourceSha });
    }
  }, [file, loadSourceFile, loadTargetFile, projectId, sourceSha, targetSha]);

  return {
    sourceFile: sourceIsLoading ? undefined : sourceFile,
    targetFile: targetIsLoading ? undefined : targetFile,
  };
};
