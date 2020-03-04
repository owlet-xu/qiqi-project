import { ipcMain, BrowserWindow } from 'electron';

// import logger from '../common/logger';
import { eventQueue } from './event-queue';
import { IpcEventType } from './ipc-event-type';
import {
  closeCadWindow,
  getCadWindow,
  getIncidentWindow
} from '../ui/cad-window';

export function startAllListeners() {
  ipcMain.on(IpcEventType.BASE.APP_EXIT, (event: any, args: any) => {
    closeCadWindow();
  });

  ipcMain.on(IpcEventType.BASE.LOGIN_SUCCESS, (event: any, args: any) => {
    if (args) {
      const [
        userInfo,
        roleInfo,
        centerInfo,
        seatInfo,
        functionGroups,
        token,
        relatedDepatments
      ] = args;
      const g: any = global;
      g.userInfo = userInfo;
      g.roleInfo = roleInfo;
      g.centerInfo = centerInfo;
      g.seatInfo = seatInfo;
      g.functionGroups = functionGroups;
      g.token = token;
      g.relatedDepatments = relatedDepatments;
    }
  });

  ipcMain.on(IpcEventType.BASE.LOGOUT, (event: any, args: any) => {
    eventQueue.clean();
  });

  ipcMain.on(IpcEventType.BASE.WINDOW_MAX, (event: any, args: any) => {
    getCadWindow().maximize();
  });

  ipcMain.on(IpcEventType.BASE.WINDOW_MIN, (event: any, args: any) => {
    getCadWindow().minimize();
  });

  ipcMain.on(
    IpcEventType.INCIDENT_CHANGE.INCIDENT_SELECTED,
    (event: any, args: any) => {
      if (args) {
        const [message] = args;
        const win: BrowserWindow = getCadWindow();
        win.webContents.send(
          IpcEventType.INCIDENT_CHANGE.INCIDENT_SELECTED,
          message
        );
      }
    }
  );

  ipcMain.on(IpcEventType.SWITCH.CHANGE_LANGUAGE, (event: any, args: any) => {
    if (args) {
      const [command] = args;
      const win: BrowserWindow = getIncidentWindow();
      win.webContents.send(IpcEventType.SWITCH.CHANGE_LANGUAGE, command);
    }
  });
}
