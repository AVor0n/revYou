import { Card } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { type Homework } from '@domains';
import { getMySolutions, getUserRole } from 'app';
import { AuthorAndDeadlines, DescriptionHeader, Desctiption, GitLabLink, SendSolutionForm } from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
}

export const DescriptionTab = ({ homeworkInfo }: DescriptionTabProps) => {
  const role = useSelector(getUserRole);
  const solutionSent = !!useSelector(getMySolutions)?.length;

  if (!homeworkInfo) {
    return null;
  }

  const renderFooter = () => {
    if (role !== '[STUDENT]') {
      return null;
    }

    if (!solutionSent) {
      return <SendSolutionForm homeworkId={homeworkInfo.id} />;
    }

    return null;
  };

  return (
    <Card view="raised" style={{ padding: 20 }} className={styles.DescriptionTab}>
      <DescriptionHeader homeworkInfo={homeworkInfo} />

      <AuthorAndDeadlines homeworkInfo={homeworkInfo} />

      <Desctiption homeworkInfo={homeworkInfo} />

      <GitLabLink repositoryLink={homeworkInfo.repositoryLink} />

      {renderFooter()}
    </Card>
  );
};
