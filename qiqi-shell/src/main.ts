import { app } from 'electron';

import logger from './common/logger';
import { startGlobalErrorHandle } from './common/error-handler';
import { startAllListeners } from './ipc/ipc-handler';
import { createCadWindow, openCadWindow, getCadWindow } from './ui/cad-window';
import { InnoSetupUpdater } from './lib/inno-setup-updater';

startGlobalErrorHandle();

if (!app.requestSingleInstanceLock()) {
  app.quit();
} else {
  app.once('ready', () => {
    logger.info('app ready');
    createCadWindow();
    openCadWindow();
    startAllListeners();

    InnoSetupUpdater();
  });
}

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (getCadWindow() === null) {
    createCadWindow();
    openCadWindow();
  }
});
