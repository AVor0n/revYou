import { Card } from '@gravity-ui/uikit';
import { type Homework } from '@domains';
import { AuthorAndDeadlines, DescriptionHeader, Desctiption, GitLabLink, SendSolutionForm } from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
}

export const DescriptionTab = ({ homeworkInfo }: DescriptionTabProps) => (
  <Card view="raised" style={{ padding: 20 }} className={styles.DescriptionTab}>
    {/* HEADER */}
    {(!!homeworkInfo?.topic || !!homeworkInfo?.lecture?.name || homeworkInfo?.departments) && (
      <DescriptionHeader homeworkInfo={homeworkInfo} />
    )}
    {/* HEADER */}

    {/* КОМУ СДАВАТЬ, ДЕДЛАЙНЫ */}
    {(!!homeworkInfo?.author ||
      !!homeworkInfo?.startDate ||
      !!homeworkInfo?.completionDeadline ||
      homeworkInfo?.reviewDuration) && <AuthorAndDeadlines homeworkInfo={homeworkInfo} />}
    {/* КОМУ СДАВАТЬ, ДЕДЛАЙНЫ */}

    {/* Описание */}
    {!!homeworkInfo?.description && <Desctiption homeworkInfo={homeworkInfo} />}
    {/* Описание */}

    {/* Ссылка на задание в gitlab */}
    {!!homeworkInfo?.repositoryLink && <GitLabLink repositoryLink={homeworkInfo.repositoryLink} />}

    <SendSolutionForm />
  </Card>
);
