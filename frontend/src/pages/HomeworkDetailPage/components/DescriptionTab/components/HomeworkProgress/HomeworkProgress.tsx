import { ProgressBar } from '@components/ProgressBar';
import { type SolutionStatus } from '@domains';

interface HomeworkProgressProps {
  status?: SolutionStatus;
}

const stages = [null, 'REVIEW_STAGE', 'REVIEWER_STAGE', 'COMPLETE'];

export const HomeworkProgress = ({ status }: HomeworkProgressProps) => {
  const progress = Math.max(
    stages.findIndex(it => it === status),
    0,
  );

  return (
    <ProgressBar
      progress={progress}
      stages={[
        {
          label: 'Задание выдано',
          tooltip: 'Необходимо выполнить задание и отправить решение на проверку',
        },
        {
          label: 'Решение отправлено',
          tooltip: 'Необходимо дождаться обратной связи от ревьюера и исправить все замечания, если таковые возникнут',
        },
        {
          label: 'Решение принято',
          tooltip: 'Решение зачтено, теперь необходимо поделиться своими знаниями и проверить работы других студентов',
        },
        {
          label: 'Задание выполнено!',
          tooltip: 'Задание полностью зачтено, можно переходить к выполнению следующих',
        },
      ]}
    />
  );
};
