import { Code } from '@gravity-ui/icons';
import { Icon } from '@gravity-ui/uikit';
import { NavLink, Outlet } from 'react-router-dom';
import { ThemeSwitcher } from '../ThemeSwitcher';
import styles from './Layout.module.scss';

interface LayoutProps {
  navLinks?: { title: string; href: string; onClick?: () => void }[];
}

export const Layout = ({ navLinks }: LayoutProps) => (
  <div className={styles.page}>
    <div className={styles.header}>
      <div className={styles.wrapper}>
        <div className={styles.headerContent}>
          <NavLink to="/">
            <Icon className={styles.logo} data={Code} size={20} />
          </NavLink>

          <div className={styles.rightContent}>
            <ThemeSwitcher className={styles.themeSwitcher} />
            {!!navLinks?.length && (
              <nav className={styles.navLinks}>
                {navLinks.map(({ title, href, onClick }) => (
                  <NavLink className={styles.navLink} to={href} key={href} onClick={onClick}>
                    {title}
                  </NavLink>
                ))}
              </nav>
            )}
          </div>
        </div>
      </div>
    </div>

    <div className={styles.content}>
      <div className={styles.wrapper}>
        <div className={styles.pageContent}>
          <Outlet />
        </div>
      </div>
    </div>
  </div>
);
