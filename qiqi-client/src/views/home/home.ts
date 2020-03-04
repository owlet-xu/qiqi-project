import { Vue, Component, Watch } from 'vue-property-decorator';
import { Action, Getter } from 'vuex-class';
import { EventType } from '@/utils/ipc/ipc-event-type';
import { IpcRenderer } from '@/utils/ipc/ipc-renderer';
import { WebviewTag } from 'electron';
// tools
import { getInjectjs } from '@/utils/injectjs';


@Component
export default class Home extends Vue {
  name = '';
  title = '';
  url = 'https://www.baidu.com/';
  searchWorld = '';
  showMessage = false;
  webview!: WebviewTag;

  mounted() {
    this.webview = this.$refs['myWebView'] as WebviewTag;
    this.webview.addEventListener('new-window', (e: any) => {
      this.url = e.url;
      console.log(this.url, '-----url');
    });
    this.webview.addEventListener('did-finish-load', (e: any) => {
        this.title = this.webview.getTitle();
        // this.search = this.url;
    });
    this.webview.addEventListener('dom-ready', () => {});
  }



  goSearch() {
    if (this.searchWorld) {
      this.url = `https://www.baidu.com/s?wd=${this.searchWorld}`;
      this.webview.loadURL(this.url);
    }
  }

  handleMinimize() {
    IpcRenderer.send(EventType.BASE.WINDOW_MIN);
  }

  handleMaximize() {
    IpcRenderer.send(EventType.BASE.WINDOW_MAX);
  }

  handleClose() {
    IpcRenderer.send(EventType.BASE.APP_EXIT);
  }

  async alyzeWeb() {
    this.webview.openDevTools();
    const jsStr = await getInjectjs('/injectjs/inject.js');
    this.webview.executeJavaScript(jsStr);
    this.webview.executeJavaScript('baiduApi.getSearchContent()', false, (res: any) => {
      console.log(res);
    });
    // this.showMessage = true;
  }

  pre() {
    this.webview.executeJavaScript('baiduApi.prePage()');
  }

  next() {
    this.webview.executeJavaScript('baiduApi.nextPage()');
  }

  goBack() {
    this.webview.goBack();
  }

  goForward() {
    this.webview.goForward();
  }
}
