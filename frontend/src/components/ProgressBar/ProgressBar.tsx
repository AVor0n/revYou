import { Tooltip } from '@gravity-ui/uikit';
import clsx from 'clsx';
import { type ReactElement } from 'react';
import styles from './ProgressBar.module.scss';

export interface Stage {
  node?: ReactElement;
  className?: string;
  label?: string;
  tooltip?: string;
}

interface ProgressBarProps {
  progress: number;
  stages: Stage[];
  lineClassName?: string;
}

export const ProgressBar = ({ stages, progress, lineClassName }: ProgressBarProps) => (
  <div className={styles.container}>
    {stages.slice(0, -1).map((_stage, idx) => (
      <div
        className={clsx(styles.line, lineClassName, idx < progress && styles.done, idx === progress && styles.next)}
        key={idx}
      />
    ))}

    <div className={styles.stagesContainer}>
      {stages.map((stage, idx) => (
        <div className={styles.stageContainer} key={idx}>
          <div
            className={clsx(
              styles.label,
              idx === 0 && styles.first,
              idx === stages.length - 1 && styles.last,
              idx === progress ? styles.bottom : styles.top,
            )}
          >
            {stage.label}
          </div>
          <Tooltip content={stage.tooltip} openDelay={0} placement={idx === progress ? 'bottom' : 'top'}>
            {stage.node ?? (
              <div
                className={clsx(
                  styles.stage,
                  idx <= progress && styles.done,
                  idx === progress && styles.current,
                  idx - 1 === progress && styles.next,
                  stage.className,
                )}
              />
            )}
          </Tooltip>
        </div>
      ))}
    </div>
  </div>
);
