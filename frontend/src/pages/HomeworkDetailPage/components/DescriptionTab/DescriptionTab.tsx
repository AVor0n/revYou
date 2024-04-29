import { Card } from '@gravity-ui/uikit';
import { type Homework } from '@domains';
import { AuthorAndDeadlines, DescriptionHeader, Desctiption, GitLabLink, SendSolutionForm } from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
}

export const DescriptionTab = ({ homeworkInfo }: DescriptionTabProps) =>
  homeworkInfo && (
    <Card view="raised" style={{ padding: 20 }} className={styles.DescriptionTab}>
      <DescriptionHeader homeworkInfo={homeworkInfo} />

      <AuthorAndDeadlines homeworkInfo={homeworkInfo} />

      <Desctiption homeworkInfo={homeworkInfo} />

      <GitLabLink repositoryLink={homeworkInfo.repositoryLink} />

      <SendSolutionForm />
    </Card>
  );
