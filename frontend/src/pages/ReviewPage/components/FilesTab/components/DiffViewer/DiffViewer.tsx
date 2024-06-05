import { Text } from '@gravity-ui/uikit';
import { useAppSelector } from 'app/hooks';
import { FileDiffReview } from './components';
import styles from './DiffViewer.module.scss';

export const DiffViewer = () => {
  const { activeFile, reviewInfo } = useAppSelector(state => state.review);

  if (!activeFile || !reviewInfo) {
    return (
      <div className={styles.placeholderContainer}>
        <Text variant="body-2" color="hint">
          Выберите файл для просмотра изменений
        </Text>
      </div>
    );
  }

  return <FileDiffReview review={reviewInfo} activeFile={activeFile} />;
};
