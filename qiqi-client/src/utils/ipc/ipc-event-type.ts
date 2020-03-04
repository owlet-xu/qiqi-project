/**
 * electron事件注册器
 */

export const EventType = {
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
  // 警情变更
  INCIDENT_CHANGE: {
    COMBINED_INCIDENT: 'combined_incident', // 警情合并
    FINISH_INCIDENT: 'finish_incident', // 结束警情 （cad结束警情通知Gis）
    FINISH_INCIDENT_TOCAD: 'finish_incident_tocad', // 结束警情 （Gis结束警情通知cad）
    DISPATCH_RESOURCE: 'dispatch_resource', // 资源调派
    DISPATCH_STATE_CHANGE: 'dispatch_state_change', // 修改资源状态
    SELECT_HIGHTLIGHT_RESOURCE: 'select_hightlight_resource', // 选中的资源高亮
    ONEKEY_DISPATCH: 'onekey_dispatch', // 一键调派
    INCIDENT_NOTICE: 'incident_notice', // 对警情修改通知
    REFRESH_TIME_SHEET: 'refresh_time_sheet' // gis修改警情时通知处警
  },
  // 地址
  ADDRESS: {
    QUERY_ADDRESS_PLOT: 'query_address_plot',  // 地址列表标绘
    QUERY_ADDRESS_SELECT: 'query_address_select', // 地址选中
    MANUAL_POSITIONING: 'manual_positioning', // 手动定位
    ADDRESS_INFO: 'address_info', // 单个地址标绘
    CLEAR_ADDRESS: 'clear_address', // 清除地址标绘
    ADDRESS_BACKFILL: 'address_backfill', // 地址回填
    // 地址分页标绘
    PAGE_ADDRESS_PLOT: 'page_address_plot' ,
    // 查询地址列表返回
    QUERY_ADDRESSES_BACKFILL: 'qeury_addresses_backfill'
  },
  // 任务
  TASK: {
    ROUTE_PLAN: 'route_plan' // 路径规划
  },
  // 通讯
  COMMUNICATION: {
    DEVICE_ACCESS_CALL: 'device_access_call', // 设备能力呼叫事件
    // 发送短信
    SEND_MESSAGE: 'send_message',
    // 环境监听
    ENVIRONMENT_MONITOR: 'environment_monitor'
  },
  // 其他
  OTHERS: {
    UPLOAD_SINGLE: 'upload_single', // 单文件上传
    UPLOAD_THEMATICMAP_SUCCESS: 'upload_thematicmap_success', // 专题图保存成功
    DOWNLOAD_FILE_COMPLETED: 'download_file_completed', // 文件下载完成
    MAP_READY: 'map_ready', // 地图初始化完成
    OPEN_FILE: 'open_file', // 打开文件夹
    PRINTER_INFO: 'printer_info', // 打印返回信息
    OPEN_BROWSER: 'open_browser'  // 浏览器打开Url
  },
  // 接警相关
  APPEAL: {
    PLOT_RECEIVING_ALARM: 'plot_receiving_alarm', // 标绘正在接警
    CLEAR_RECEIVING_ALARM: 'clear_receiving_alarm', // 清除警情标会事件（接警表单关闭）
    SELECTED_ADDRESS: 'selected_address', // 地名库选中
  },
  // 处警相关
  DISPOSAL: {
    // 车辆抓拍摄像头位置
    VIDEO_ANALYSIS_SNAPSHOT_CAMERA: 'video_analysis_snapshot_camera',
    // 分组修改
    GROUP_CHANGED: 'group_changed'
  },
  // 连接打印机打印
  PRINTER: {
    GET_PRINTER_LIST: 'get_printer_list', // cad获取打印机清单
    SEND_PRINTER_LIST: 'send_printer_list' // 发送cad打印机清单
  }
};
