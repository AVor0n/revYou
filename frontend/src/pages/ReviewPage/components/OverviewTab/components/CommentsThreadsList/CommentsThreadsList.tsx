import { Select, Text } from '@gravity-ui/uikit';
import { useMemo, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useGetAllThreadsQuery, type CommentsThread, type Review } from '@api';
import { isFile, type FileNode } from '@shared/types';
import { EmptyPlug, Link, findInTree } from '@ui';
import { useAppSelector } from 'app/hooks';
import { CommentsThreadWithDiff } from './components';
import styles from './CommentsThreadsList.module.scss';

interface CommentsThreadsListProps {
  review: Review;
}

export const CommentsThreadsList = ({ review }: CommentsThreadsListProps) => {
  const [filterValue, setFilterValue] = useState(['all']);
  const [sortValue, setSortValue] = useState(['ASC']);
  const { homeworkId, reviewId, role } = useParams<Record<'homeworkId' | 'reviewId' | 'role', string>>();
  const { data: threads } = useGetAllThreadsQuery({ reviewId: review.reviewId });
  const { filesTree } = useAppSelector(state => state.review);

  const threadsWithFileInfo = useMemo(() => {
    if (!threads || !filesTree) return [];

    return threads.data.reduce((acc: { thread: CommentsThread; fileInfo: FileNode }[], thread) => {
      const treeItem = findInTree(filesTree, item => {
        if (!isFile(item)) return false;
        return thread.filePath === item.oldPath || thread.filePath === item.path;
      });

      if (treeItem && isFile(treeItem)) {
        acc.push({ thread, fileInfo: treeItem });
      }
      return acc;
    }, []);
  }, [filesTree, threads]);

  const filteredThreads = useMemo(() => {
    if (filterValue[0] === 'all') return threadsWithFileInfo;
    return threadsWithFileInfo.filter(({ thread }) => thread.status === filterValue[0]);
  }, [filterValue, threadsWithFileInfo]);

  const sortedThreads = useMemo(() => {
    const sortDirection = sortValue[0] === 'ASC' ? -1 : 1;
    return filteredThreads.sort(
      (a, b) => b.thread.comments[0]!.createdAt.localeCompare(a.thread.comments[0]!.createdAt) * sortDirection,
    );
  }, [filteredThreads, sortValue]);

  return (
    <>
      <div className={styles.filters}>
        <div className={styles.filter}>
          Порядок:
          <Select
            value={sortValue}
            onUpdate={setSortValue}
            options={[
              { value: 'ASC', content: 'Сначала старые' },
              { value: 'DESC', content: 'Сначала новые' },
            ]}
          />
        </div>
        <div className={styles.filter}>
          Статуc:
          <Select
            value={filterValue}
            onUpdate={setFilterValue}
            options={[
              { value: 'all', content: 'Все' },
              { value: 'ACTIVE', content: 'Активные' },
              { value: 'RESOLVED', content: 'Решенные' },
            ]}
          />
        </div>
      </div>

      <div className={styles.container}>
        {sortedThreads.map(({ thread, fileInfo }) => (
          <CommentsThreadWithDiff
            thread={thread}
            key={thread.threadId}
            file={fileInfo}
            sourceSha={review.sourceCommitId}
            targetSha={review.commitId}
          />
        ))}

        {threadsWithFileInfo.length !== 0 && filteredThreads.length === 0 && (
          <div className={styles.placeholder}>
            <Text variant="body-3" color="hint">
              Нет комментариев удовлетворяющих фильтрам
            </Text>
          </div>
        )}

        {threads?.data.length === 0 && (
          <EmptyPlug
            primaryText="Комментариев не найдено"
            secondaryText={
              role !== 'student' && (
                <>
                  Оставьте их на
                  <Link href={`/homeworks/${homeworkId}/review/${reviewId}/${role}/files`}> странице файлов</Link>
                </>
              )
            }
          />
        )}
      </div>
    </>
  );
};
