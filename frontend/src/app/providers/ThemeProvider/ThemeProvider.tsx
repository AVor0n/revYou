import { ThemeProvider as ThemeProviderGravity } from '@gravity-ui/uikit';
import { useMemo, useState, type PropsWithChildren } from 'react';
import { LOCAL_STORAGE_THEME_KEY, Theme, ThemeContext } from './themeContext';

const defaultTheme = localStorage.getItem(LOCAL_STORAGE_THEME_KEY) || Theme.LIGHT;

interface ThemeProviderProps extends PropsWithChildren {
  initialTheme?: Theme;
}

export const ThemeProvider = (props: ThemeProviderProps) => {
  const { initialTheme, children } = props;

  const [theme, setTheme] = useState<Theme>(initialTheme || (defaultTheme as Theme));

  const defaultProps = useMemo(
    () => ({
      theme,
      setTheme,
    }),
    [theme],
  );

  return (
    <ThemeContext.Provider value={defaultProps}>
      <ThemeProviderGravity theme={defaultProps.theme}>{children}</ThemeProviderGravity>
    </ThemeContext.Provider>
  );
};
