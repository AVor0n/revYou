import { Card } from '@gravity-ui/uikit';
import { type Homework } from '@domains';
import { AuthorAndDeadlines, DescriptionHeader, Desctiption, GitLabLink, SendSolutionForm } from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
}

export const DescriptionTab = ({ homeworkInfo }: DescriptionTabProps) => (
  <Card view="raised" style={{ padding: 20 }} className={styles.DescriptionTab}>
    {(!!homeworkInfo?.topic || !!homeworkInfo?.lecture?.name || homeworkInfo?.departments) && (
      <DescriptionHeader homeworkInfo={homeworkInfo} />
    )}

    {(!!homeworkInfo?.author ||
      !!homeworkInfo?.startDate ||
      !!homeworkInfo?.completionDeadline ||
      homeworkInfo?.reviewDuration) && <AuthorAndDeadlines homeworkInfo={homeworkInfo} />}

    {!!homeworkInfo?.description && <Desctiption homeworkInfo={homeworkInfo} />}

    {!!homeworkInfo?.repositoryLink && <GitLabLink repositoryLink={homeworkInfo.repositoryLink} />}

    <SendSolutionForm />
  </Card>
);
