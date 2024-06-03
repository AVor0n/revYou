import { Loader } from '@gravity-ui/uikit';
import { MonacoDiffEditor } from 'react-monaco-editor';
import { defaultDiffEditorOptions, useResizableDiffEditor } from '@components/MonacoEditor';
import { type Review } from '@domains';
import { Theme, useTheme } from 'app';
import { useFileDiffComments } from '../../hooks';
import { CreateThreadWidget, EditorThreadComments } from './components';
import { useScrollToChangeOrComment } from './hooks';
import { useFileDiffContent } from './hooks/useFileDiffContent';
import styles from './FileDiffReview.module.scss';

interface FileDiffReviewProps {
  review: Review;
  activeFilePath: string;
}

export const FileDiffReview = ({ review, activeFilePath }: FileDiffReviewProps) => {
  const { theme } = useTheme();
  const { diffEditor, editorDidMount } = useResizableDiffEditor();
  const { sourceCommitId: sourceSha, commitId: targetSha, reviewId } = review;
  const { sourceCommentsThreads, targetCommentsThreads, allThreads } = useFileDiffComments();
  const { sourceFile, targetFile } = useFileDiffContent({ review, filePath: activeFilePath });
  useScrollToChangeOrComment({ editor: diffEditor, threads: allThreads });

  if (sourceFile === undefined || targetFile === undefined) {
    return (
      <div className={styles.placeholderContainer}>
        <Loader size="m" />
      </div>
    );
  }

  return (
    <>
      <MonacoDiffEditor
        theme={theme === Theme.LIGHT ? 'vs' : 'vs-dark'}
        height="80vh"
        // TODO: сделать автоопределение языка на основе расширения файла
        language="typescript"
        original={sourceFile ?? ''}
        value={targetFile ?? ''}
        options={defaultDiffEditorOptions}
        editorDidMount={editorDidMount}
      />
      {diffEditor && sourceCommentsThreads && targetCommentsThreads && (
        <>
          <EditorThreadComments editor={diffEditor.getOriginalEditor()} threads={sourceCommentsThreads} />
          <EditorThreadComments editor={diffEditor.getModifiedEditor()} threads={targetCommentsThreads} />
          <CreateThreadWidget editor={diffEditor.getOriginalEditor()} commitSha={sourceSha} reviewId={reviewId} />
          <CreateThreadWidget editor={diffEditor.getModifiedEditor()} commitSha={targetSha} reviewId={reviewId} />
        </>
      )}
    </>
  );
};
