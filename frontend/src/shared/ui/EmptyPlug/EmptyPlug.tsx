import { Text } from '@gravity-ui/uikit';
import type { ReactNode } from 'react';
import styles from './EmptyPlug.module.scss';

interface EmptyPlugProps {
  primaryText: ReactNode;
  secondaryText: ReactNode;
}

export const EmptyPlug = ({ primaryText, secondaryText }: EmptyPlugProps) => (
  <div className={styles.empty}>
    <img className={styles.image} src="/cat.png" alt="cat" width={300} height={180} />
    <Text variant="display-1" color="hint">
      {primaryText}
    </Text>
    <Text variant="body-2" color="hint">
      {secondaryText}
    </Text>
  </div>
);
