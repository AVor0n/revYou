import { Button, Modal, Text } from '@gravity-ui/uikit';
import type { FormEventHandler, ReactNode } from 'react';
import styles from './FormWindow.module.scss';

interface FormWindowProps {
  /** Состояние видимости окна */
  open: boolean;
  /** Заголовок */
  title: string;
  /** Контент формы */
  children: ReactNode;
  /** Обработчик сохранения формы */
  onSubmit: FormEventHandler;
  /** Обработчик закрытия окна */
  onClose: () => void;
  /** Текст кнопки сохранения. По умолчанию 'Сохранить' */
  saveText?: string;
  /** Текст кнопки сохранения. По умолчанию 'Отмена' */
  cancelText?: string;
  /** Кастомный футер */
  footer?: ReactNode;
  /** Показать индикатор загрузки на кнопке сохранения */
  saveLoading?: boolean;
  /** Заблокировать кнопку сохранения */
  saveDisabled?: boolean;
  /** Заблокировать кнопку отмены */
  cancelDisabled?: boolean;
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
  saveLoading,
  saveDisabled,
  cancelDisabled,
}: FormWindowProps) => (
  <Modal open={open} disableOutsideClick onClose={onClose}>
    <form className={styles.window} onSubmit={onSubmit}>
      <header className={styles.header}>
        <Text variant="header-1">{title}</Text>
      </header>

      <main className={styles.content}>{children}</main>

      {footer || (
        <footer className={styles.footer}>
          <Button size="l" onClick={onClose} disabled={cancelDisabled}>
            {cancelText ?? 'Отмена'}
          </Button>
          <Button view="action" size="l" type="submit" disabled={saveDisabled} loading={saveLoading}>
            {saveText ?? 'Сохранить'}
          </Button>
        </footer>
      )}
    </form>
  </Modal>
);
