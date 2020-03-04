import { BrowserWindow, app } from 'electron';
import path from 'path';
import url from 'url';
import config from '../lib/config';

const cadWindowConfig = config.cadWindowConfig;
const appPath = app.getAppPath();
const preloadPath = path.join(appPath, 'main/preload.js');

let window: BrowserWindow | null;
let incidentWindow: BrowserWindow | null; // 警情选择子窗口
let loadUrl = ''; // 加载的url

export function createCadWindow() {
  window = new BrowserWindow({
    minWidth: 1600,
    minHeight: 900,
    frame: cadWindowConfig.frame,
    show: false,
    fullscreen: cadWindowConfig.fullScreen,
    resizable: cadWindowConfig.resizable,
    webPreferences: {
      nodeIntegration: cadWindowConfig.webPreferences.nodeIntegration,
      preload: preloadPath
    }
  });

  window.on('ready-to-show', () => {
    if (window) {
      window.show();
      // window.maximize();
    }
  });

  window.on('close', event => {
    closeCadWindow();
  });

  window.on('closed', () => {
    window = null;
  });

  return window;
}

export function openCadWindow() {
  if (!window) return;

  if (cadWindowConfig.openDevTools) {
    window.webContents.openDevTools();
  }


  if (cadWindowConfig.isDebug) {
    loadUrl = cadWindowConfig.loadURL;
  } else {
    loadUrl = url.format({
      pathname: path.resolve(
        app.getAppPath(),
        'renderer',
        cadWindowConfig.loadPath
      )
    });
  }
  window.loadURL(loadUrl);
  setChildWindow(window);
}

export function closeCadWindow() {
  if (window) {
    window.close();
  }
}

export function getCadWindow(): BrowserWindow {
  return window as BrowserWindow;
}

export function getIncidentWindow(): BrowserWindow {
  return incidentWindow as BrowserWindow;
}

function setChildWindow(win: BrowserWindow) {
  if (!win) {
    return;
  }
  win.webContents.on('new-window', (event: any, urls: string, fname: any, disposition: any, options: any) => {
    event.preventDefault();
    incidentWindow = new BrowserWindow({
      height: options.height,
      width: options.width,
      frame: true,
      show: true,
      autoHideMenuBar: true,
      webPreferences: {
        nodeIntegration: true,
        nativeWindowOpen: true,
        affinity: 'main-window',
        preload: preloadPath
      }
    });
    incidentWindow.setAlwaysOnTop(true);
    if (cadWindowConfig.openDevTools) {
      incidentWindow.webContents.openDevTools();
    }
    setChildWindow(incidentWindow);
    const urlss = loadUrl + urls.substring(urls.indexOf('#/'), urls.length);
    console.log(urlss, '[cad-window, setChildWindow]-->incident selected window url');
    incidentWindow.loadURL(urlss);
    event.newGuest = incidentWindow;
    return incidentWindow;
  });
}
