import { Moon, Sun } from '@gravity-ui/icons';
import cls from 'clsx';
import { Theme, useTheme } from 'app/providers';
import styles from './ThemeSwitcher.module.scss';

interface ThemeSwitcherProps {
  className?: string;
}

export const ThemeSwitcher = ({ className }: ThemeSwitcherProps) => {
  const { theme, toggleTheme } = useTheme();

  return (
    <div className={cls(styles.ThemeSwitcher, className)}>
      {theme === Theme.DARK ? (
        <Moon onClick={toggleTheme} cursor="pointer" />
      ) : (
        <Sun onClick={toggleTheme} cursor="pointer" color="white" />
      )}
    </div>
  );
};
