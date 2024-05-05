import { Card } from '@gravity-ui/uikit';
import { useSelector } from 'react-redux';
import { type Homework } from '@domains';
import { getUserRole } from 'app';
import { AuthorAndDeadlines, DescriptionHeader, Desctiption, GitLabLink, SendSolutionForm } from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
}

export const DescriptionTab = ({ homeworkInfo }: DescriptionTabProps) => {
  const role = useSelector(getUserRole);

  if (!homeworkInfo) {
    return null;
  }

  return (
    <Card view="raised" style={{ padding: 20 }} className={styles.DescriptionTab}>
      <DescriptionHeader homeworkInfo={homeworkInfo} />

      <AuthorAndDeadlines homeworkInfo={homeworkInfo} />

      <Desctiption homeworkInfo={homeworkInfo} />

      <GitLabLink repositoryLink={homeworkInfo.repositoryLink} />

      {role === '[STUDENT]' && <SendSolutionForm />}
    </Card>
  );
};
