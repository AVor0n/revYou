import { Button, Card, TextArea } from '@gravity-ui/uikit';
import { useState } from 'react';
import styles from './CreateThreadCard.module.scss';

interface CreateThreadCardProps {
  onCreate: (text: string) => void;
  onCancel: () => void;
  isLoading?: boolean;
}

export const CreateThreadCard = ({ onCreate, onCancel, isLoading }: CreateThreadCardProps) => {
  const [value, setValue] = useState('');

  return (
    <Card view="raised" className={styles.commentCard} disabled={isLoading}>
      <TextArea value={value} onChange={event => setValue(event.target.value)} placeholder="Ваш комментарий..." />
      <Button view="action" disabled={!value} onClick={() => onCreate(value)}>
        Отправить
      </Button>
      <Button view="outlined" onClick={onCancel}>
        Отмена
      </Button>
    </Card>
  );
};
