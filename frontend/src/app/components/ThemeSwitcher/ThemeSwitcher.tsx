import { Moon, Sun } from '@gravity-ui/icons';
import { Theme, useTheme } from 'app/providers';

interface ThemeSwitcherProps {
  className?: string;
}

export const ThemeSwitcher = ({ className }: ThemeSwitcherProps) => {
  const { theme, toggleTheme } = useTheme();

  return (
    <div className={className}>
      {theme === Theme.DARK ? (
        <Moon onClick={toggleTheme} cursor="pointer" />
      ) : (
        <Sun onClick={toggleTheme} cursor="pointer" />
      )}
    </div>
  );
};
