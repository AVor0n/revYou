import { Label } from '@gravity-ui/uikit';

interface ReviewStatusProps {
  status: string;
}

export const ReviewStatus = ({ status }: ReviewStatusProps) => {
  switch (status) {
    case 'REVIEWER_SEARCH':
      return <Label theme="info">Поиск ревьюера</Label>;
    case 'REVIEWER_FOUND':
      return <Label theme="info">Ожидание начала ревью</Label>;
    case 'REVIEW_STARTED':
      return <Label theme="utility">Ожидание окончания ревью</Label>;
    case 'CORRECTIONS_REQUIRED':
      return <Label theme="warning">Требуются правки</Label>;
    case 'CORRECTIONS_LOADED':
      return <Label theme="utility">Ожидание повторного ревью</Label>;
    case 'APPROVED':
      return <Label theme="success">Принято</Label>;
    default:
      return <Label theme="unknown">unknown</Label>;
  }
};
