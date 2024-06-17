import { Skeleton } from '@gravity-ui/uikit';
import { useEffect, useMemo, useRef } from 'react';
import { Panel, PanelGroup, type ImperativePanelGroupHandle } from 'react-resizable-panels';
import { useParams } from 'react-router-dom';
import { useLazyGetReviewsInfoQuery, type ReviewStatus, type ReviewInfo } from '@api';
import { useApiError } from '@shared/utils';
import { EmptyPlug } from '@ui';
import { StateColumn } from './components';
import styles from './AllSolutionsTab.module.scss';

const reviewStatuses: ReviewStatus[] = [
  'REVIEWER_SEARCH',
  'REVIEWER_FOUND',
  'REVIEW_STARTED',
  'CORRECTIONS_REQUIRED',
  'CORRECTIONS_LOADED',
  'APPROVED',
];

export const AllSolutionsTab = () => {
  const { homeworkId } = useParams<{ homeworkId: string }>();
  const [loadAllSolutions, { data: allSolutions, error }] = useLazyGetReviewsInfoQuery();
  useApiError(error, { name: 'loadAllSolutions', title: 'Не удалось загрузить список решений' });
  const ref = useRef<ImperativePanelGroupHandle>(null);

  useEffect(() => {
    if (homeworkId) {
      loadAllSolutions({ homeworkId: +homeworkId });
    }
  }, [homeworkId, loadAllSolutions]);

  const solutionsByStatus = useMemo(() => {
    const initSolutionsByStatus = Object.fromEntries(reviewStatuses.map(status => [status, [] as ReviewInfo[]]));
    if (!allSolutions) return initSolutionsByStatus;

    return allSolutions.data.reduce((acc, review) => {
      acc[review.status]?.push(review);
      return acc;
    }, initSolutionsByStatus);
  }, [allSolutions]);

  if (!allSolutions) {
    return <Skeleton className={styles.skeleton} />;
  }

  if (allSolutions.data.length === 0) {
    return <EmptyPlug primaryText="Решений ещё нет" secondaryText="Нужно немного подождать..." />;
  }

  return (
    <PanelGroup className={styles.page} direction="horizontal" ref={ref}>
      {reviewStatuses.map(status => {
        const reviewsList = solutionsByStatus[status] ?? [];

        return (
          <Panel
            className={styles.column}
            minSize={10}
            onMouseEnter={() => {
              if (!reviewsList.length) return;
              ref.current?.setLayout(reviewStatuses.map(s => (s === status ? 25 : 15)));
            }}
            key={status}
          >
            <StateColumn reviewList={reviewsList} status={status} />
          </Panel>
        );
      })}
    </PanelGroup>
  );
};
