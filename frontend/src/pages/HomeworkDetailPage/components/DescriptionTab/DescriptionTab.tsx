import { Card, Skeleton } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useLazyGetHomeworkQuery } from '@api';
import { useAppSelector } from 'app/hooks';
import {
  AuthorAndDeadlines,
  DescriptionHeader,
  Desctiption,
  GitLabLink,
  HomeworkProgress,
  SendSolutionForm,
} from './components';
import styles from './DescriptionTab.module.scss';

export const DescriptionTab = () => {
  const { homeworkId } = useParams<{ homeworkId: string }>();
  const role = useAppSelector(state => state.user.authData?.role);
  const [loadHomework, { data: homeworkInfo }] = useLazyGetHomeworkQuery();
  const solutionSent = !!homeworkInfo?.status;

  useEffect(() => {
    if (homeworkId) {
      loadHomework({ homeworkId: +homeworkId });
    }
  }, [homeworkId, loadHomework]);

  if (!homeworkInfo) {
    return <Skeleton className={styles.skeleton} />;
  }

  const renderFooter = () => {
    if (role !== 'STUDENT') {
      return null;
    }

    if (!solutionSent) {
      return <SendSolutionForm homeworkId={homeworkInfo.id} />;
    }

    return null;
  };

  return (
    <div className={styles.DescriptionTab}>
      {role === 'STUDENT' && (
        <Card view="raised" className={styles.progressCard}>
          <HomeworkProgress status={homeworkInfo.status} />
        </Card>
      )}

      <Card view="raised" className={styles.homeworkCard}>
        <DescriptionHeader homeworkInfo={homeworkInfo} />

        <AuthorAndDeadlines homeworkInfo={homeworkInfo} />

        <Desctiption homeworkInfo={homeworkInfo} />

        <GitLabLink repositoryLink={homeworkInfo.repositoryLink} />

        {renderFooter()}
      </Card>
    </div>
  );
};
