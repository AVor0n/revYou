import { Button, Card } from '@gravity-ui/uikit';
import { toaster } from '@gravity-ui/uikit/toaster-singleton';
import { useSelector } from 'react-redux';
import { type Solution, type Homework } from '@domains';
import { createSolutionAttempt, getUserRole, loadSolution, useAppDispatch } from 'app';
import { AuthorAndDeadlines, DescriptionHeader, Desctiption, GitLabLink, SendSolutionForm } from './components';
import styles from './DescriptionTab.module.scss';

interface DescriptionTabProps {
  homeworkInfo: Homework | null;
  solutionInfo: Solution | null;
}

export const DescriptionTab = ({ homeworkInfo, solutionInfo }: DescriptionTabProps) => {
  const role = useSelector(getUserRole);
  const dispatch = useAppDispatch();

  if (!homeworkInfo) {
    return null;
  }

  const onSendAttempt = () => {
    dispatch(createSolutionAttempt(homeworkInfo.id)).then(() => {
      toaster.add({ name: 'sendAttempt', title: 'Решение отправлено', theme: 'success' });
      dispatch(loadSolution(homeworkInfo.id));
    });
  };

  const renderFooter = () => {
    if (role !== '[STUDENT]') {
      return null;
    }

    if (!solutionInfo) {
      return <SendSolutionForm homeworkId={homeworkInfo.id} />;
    }

    if (!solutionInfo.solutionAttempts.length) {
      return (
        <Button
          className={styles.sendTryButton}
          type="submit"
          view="action"
          size="m"
          pin="round-round"
          onClick={onSendAttempt}
        >
          Сдать попытку
        </Button>
      );
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
