import { Skeleton } from '@gravity-ui/uikit';
import { useEffect, useMemo, useRef } from 'react';
import { Panel, PanelGroup, type ImperativePanelGroupHandle } from 'react-resizable-panels';
import { loadAllSolutions, type FullReviewInfo } from 'app';
import { useAppDispatch, useAppSelector } from 'app/hooks';
import { StateColumn } from './components';
import type { Homework, ReviewStatus } from '@domains';
import styles from './AllSolutionsTab.module.scss';

interface AllSolutionsTabProps {
  homeworkInfo: Homework | null;
}

const reviewStatuses: ReviewStatus[] = [
  'REVIEWER_SEARCH',
  'REVIEWER_FOUND',
  'REVIEW_STARTED',
  'CORRECTIONS_REQUIRED',
  'CORRECTIONS_LOADED',
  'APPROVED',
];

export const AllSolutionsTab = ({ homeworkInfo }: AllSolutionsTabProps) => {
  const dispatch = useAppDispatch();
  const { allSolutions } = useAppSelector(state => state.solution);
  const ref = useRef<ImperativePanelGroupHandle>(null);

  const solutionsByStatus = useMemo(() => {
    const initSolutionsByStatus = Object.fromEntries(reviewStatuses.map(status => [status, [] as FullReviewInfo[]]));
    if (!allSolutions) return initSolutionsByStatus;

    return allSolutions.reduce((acc, review) => {
      acc[review.status]?.push(review);
      return acc;
    }, initSolutionsByStatus);
  }, [allSolutions]);

  useEffect(() => {
    if (!homeworkInfo) return;
    dispatch(loadAllSolutions(homeworkInfo.id));
  }, [dispatch, homeworkInfo]);

  if (!allSolutions) {
    return <Skeleton className={styles.skeleton} />;
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
