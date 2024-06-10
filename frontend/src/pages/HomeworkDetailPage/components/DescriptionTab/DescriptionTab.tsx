import { Card } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { type Homework } from '@domains';
import { getUserRole } from 'app';
import {
  AuthorAndDeadlines,
  DescriptionHeader,
  Desctiption,
  GitLabLink,
  HomeworkProgress,
  SendSolutionForm,
} from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
}

export const DescriptionTab = ({ homeworkInfo }: DescriptionTabProps) => {
  const role = useSelector(getUserRole);
  const solutionSent = !!homeworkInfo?.status;

  if (!homeworkInfo) {
    return null;
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
