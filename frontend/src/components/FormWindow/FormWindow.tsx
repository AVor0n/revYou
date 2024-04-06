import { Button, Modal, Text } from '@gravity-ui/uikit';
import type { ReactNode } from 'react';
import styles from './FormWindow.module.scss';

interface FormWindowProps {
  /** Состояние видимости окна */
  open: boolean;
  /** Заголовок */
  title: string;
  /** Контент формы */
  children: ReactNode;
  /** Обработчик сохранения формы */
  onSubmit: () => void;
  /** Обработчик закрытия окна */
  onClose: () => void;
  /** Текст кнопки сохранения. По умолчанию 'Сохранить' */
  saveText?: string;
  /** Текст кнопки сохранения. По умолчанию 'Отмена' */
  cancelText?: string;
  /** Кастомный футер */
  footer?: ReactNode;
}

/** Окно для показа формы создания/редактирования */
export const FormWindow = ({
  open,
  title,
  children,
  saveText,
  cancelText,
  onSubmit,
  onClose,
  footer,
}: FormWindowProps) => (
  <Modal open={open} disableOutsideClick onClose={onClose}>
    <form className={styles.window} onSubmit={onSubmit}>
      <header className={styles.header}>
        <Text variant="header-1">{title}</Text>
      </header>

      <main className={styles.content}>{children}</main>

      {footer || (
        <footer className={styles.footer}>
          <Button size="l" onClick={onClose}>
            {cancelText ?? 'Отмена'}
          </Button>
          <Button view="action" size="l" type="submit">
            {saveText ?? 'Сохранить'}
          </Button>
        </footer>
      )}
    </form>
  </Modal>
);
