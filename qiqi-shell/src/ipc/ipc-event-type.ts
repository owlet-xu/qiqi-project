export const IpcEventType = {
  // 基础
  BASE: {
    LOGOUT: 'logout', // 登出
    LOGIN_SUCCESS: 'login_success', // 登录成功
    APP_EXIT: 'app_exit', // 应用程序退出
    EXIT_PROCESS: 'exit_process', // 进程退出
    WINDOW_MIN: 'window_min', // 窗口最小
    WINDOW_MAX: 'window_max' // 窗口最大
  },
  // 切换事件
  SWITCH: {
    ROUTER_CHANGE: 'router_change', // 界面切换（参数，界面枚举）
    CHANGE_INCIDENT: 'change_incident', // 警情切换（cad切换通知Gis)
    CHANGE_INCIDENT_TOCAD: 'change_incident_tocad', // 警情切换（Gis切换通知cad）
    CHANGE_LANGUAGE: 'change_language' // 切换语言
  },
  // 连接打印机打印
  PRINTER: {
    GET_PRINTER_LIST: 'get_printer_list', // cad获取打印机清单
    SEND_PRINTER_LIST: 'send_printer_list' // 发送cad打印机清单
  },
  // 警情变更
  INCIDENT_CHANGE: {
    INCIDENT_SELECTED: 'incident_selected'
  }
};
