import { Label, Tooltip } from '@gravity-ui/uikit';
import { type SolutionStatus } from '@domains';
import styles from './StatusCircle.module.scss';

type LabelTheme = 'danger' | 'warning' | 'info' | 'utility' | 'success';

const SOLUTION_STATUS_TO_THEME: Record<SolutionStatus, LabelTheme> = {
  IN_PROGRESS: 'warning',
  REVIEW_STAGE: 'info',
  REVIEWER_STAGE: 'utility',
  COMPLETE: 'success',
};

const SOLUTION_STATUS_TO_TEXT: Record<SolutionStatus, string> = {
  IN_PROGRESS: 'ветка с решением отправлена, но ревью не запрошено',
  REVIEW_STAGE: 'решение на ревью',
  REVIEWER_STAGE: 'необходимо провести ревью чужих решений',
  COMPLETE: 'Вы прошли все этапы, домашка сделана!',
};

interface StatusCircleProps {
  status?: SolutionStatus;
}

export const StatusCircle = ({ status }: StatusCircleProps) => (
  <Tooltip openDelay={0} content={status ? SOLUTION_STATUS_TO_TEXT[status] : 'решение не отправлено'}>
    <Label className={styles.StatusCircle} theme={status ? SOLUTION_STATUS_TO_THEME[status] : 'danger'}>
      {' '}
    </Label>
  </Tooltip>
);
