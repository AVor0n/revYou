import { SquareLetterP, SquareMinus, SquarePlus, SquareXmark } from '@gravity-ui/icons';
import { Icon, type IconProps, Tooltip } from '@gravity-ui/uikit';
import clsx from 'clsx';
import type { FileStatus } from '@shared/types';
import styles from './FileChangeIcon.module.scss';

interface FileChangesIconProps {
  fileStatus: FileStatus;
}

const tooltips: Record<FileStatus, string> = {
  added: 'Файл добавлен',
  changed: 'Файл изменен',
  deleted: 'Файл удален',
  renamed: 'Файл переименован',
  unchanged: '',
};

const icons: Record<FileStatus, IconProps['data'] | null> = {
  added: SquarePlus,
  changed: SquareMinus,
  deleted: SquareXmark,
  renamed: SquareLetterP,
  unchanged: null,
};

export const FileChangesIcon = ({ fileStatus }: FileChangesIconProps) => {
  const icon = icons[fileStatus];

  if (!icon) return null;
  return (
    <Tooltip content={tooltips[fileStatus]}>
      <div className={clsx(styles.container, styles[fileStatus])}>
        <Icon data={icon} />
      </div>
    </Tooltip>
  );
};
