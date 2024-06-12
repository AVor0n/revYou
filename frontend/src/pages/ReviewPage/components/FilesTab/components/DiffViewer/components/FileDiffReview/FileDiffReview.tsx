import { Loader } from '@gravity-ui/uikit';
import { MonacoDiffEditor } from 'react-monaco-editor';
import { type Review } from '@api';
import { defaultDiffEditorOptions, useResizableDiffEditor } from '@ui';
import { Theme, useTheme, type FileNode } from 'app';
import { useFileDiffContent } from '../../../../../../hooks';
import { useFileDiffComments } from '../../hooks';
import { CreateThreadWidget, EditorThreadComments } from './components';
import { useScrollToChangeOrComment } from './hooks';
import styles from './FileDiffReview.module.scss';

interface FileDiffReviewProps {
  review: Review;
  activeFile: FileNode;
}

export const FileDiffReview = ({ review, activeFile }: FileDiffReviewProps) => {
  const { theme } = useTheme();
  const { diffEditor, editorDidMount } = useResizableDiffEditor();
  const { sourceCommitId: sourceSha, commitId: targetSha, reviewId } = review;
  const { sourceCommentsThreads, targetCommentsThreads, allThreads } = useFileDiffComments();
  const { sourceFile, targetFile } = useFileDiffContent({
    file: activeFile,
    sourceSha: review.sourceCommitId,
    targetSha: review.commitId,
  });

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
          <CreateThreadWidget
            editor={diffEditor.getOriginalEditor()}
            commitSha={sourceSha}
            reviewId={reviewId}
            filePath={activeFile.oldPath}
          />
          <CreateThreadWidget
            editor={diffEditor.getModifiedEditor()}
            commitSha={targetSha}
            reviewId={reviewId}
            filePath={activeFile.path}
          />
        </>
      )}
    </>
  );
};
