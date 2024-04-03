import { Code } from '@gravity-ui/icons';
import { Icon } from '@gravity-ui/uikit';
import { NavLink, Outlet } from 'react-router-dom';
import styles from './Layout.module.scss';

interface LayoutProps {
  navLinks?: { title: string; href: string }[];
}

export const Layout = ({ navLinks }: LayoutProps) => (
  <div className={styles.page}>
    <div className={styles.header}>
      <div className={styles.wrapper}>
        <div className={styles.headerContent}>
          <NavLink to="/">
            <Icon className={styles.logo} data={Code} size={20} />
          </NavLink>

          {!!navLinks?.length && (
            <nav className={styles.navLinks}>
              {navLinks.map(({ title, href }) => (
                <NavLink className={styles.navLink} to={href} key={href}>
                  {title}
                </NavLink>
              ))}
            </nav>
          )}
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
