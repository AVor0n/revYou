import { Panel, PanelGroup, PanelResizeHandle } from 'react-resizable-panels';
import { DiffViewer, FilesTree } from './components';
import styles from './FilesTab.module.scss';

export const FilesTab = () => (
  <PanelGroup className={styles.FilesTab} direction="horizontal">
    <Panel defaultSize={10}>
      <FilesTree />
    </Panel>

    <PanelResizeHandle className={styles.resizer} />

    <Panel defaultSize={90}>
      <DiffViewer />
    </Panel>
  </PanelGroup>
);
