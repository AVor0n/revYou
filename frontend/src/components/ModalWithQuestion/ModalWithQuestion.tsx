import { Button, Modal, Text } from '@gravity-ui/uikit';
import type { ReactNode } from 'react';
import styles from './ModalWithQuestion.module.scss';

interface ModalWithQuestionProps {
  open: boolean;
  yesText?: string;
  noText?: string;
  question: ReactNode;
  onYesClick?: () => void;
  onNoClick?: () => void;
  loading?: boolean;
}

export const ModalWithQuestion = ({
  open,
  question,
  noText = 'Нет',
  yesText = 'Да',
  loading,
  onNoClick,
  onYesClick,
}: ModalWithQuestionProps) => (
  <Modal open={open} contentClassName={styles.modal}>
    <Text variant="body-2">{question}</Text>

    <div className={styles.modalButtons}>
      <Button view="normal" width="max" onClick={onNoClick} disabled={loading}>
        {noText}
      </Button>
      <Button view="action" width="max" onClick={onYesClick} loading={loading}>
        {yesText}
      </Button>
    </div>
  </Modal>
);
