import { Subject, Subscription } from 'rxjs';

export interface EventObject {
  /**
   * 事件名称
   */
  event: string;
  /**
   * 关键字
   */
  key: string;
  /**
   * 观察者对象
   */
  subject: Subject<any>;
  /**
   * 订阅对象
   */
  subscription: Subscription;
}

// 全局，所有事件存储数组
const eventObjects: EventObject[] = [];

export const rxevent = {
  /**
   * @param {string} event - 事件名称
   * @param {string} key -关键字
   * @param {function} func - 订阅需要执行的委托
   * 订阅事件
   */
  subscribe(event: string, key: string, func: (param: any) => void) {
    // 因为页面跳转和刷新都会重新触发构造器和ngInit方法,重新构造代表着整个页面的实例都被重置了,
    // 现在的订阅一般都是在构造器里，所以传递给订阅模式的func属性都必须是基于最新实例的，否则会
    // 出现页面绑定的变量在页面来回跳转后虽然能正常的触发publish，但却无法通知界面的现象，以下
    // 代码通过删除并重新进入队列的方式，保证了订阅发布是最新的
    const index = eventObjects.findIndex((eo: EventObject) => eo.event === event && eo.key === key);
    if (index > -1) {
      eventObjects.splice(index, 1);
    }
    // 再找一次(做保护，防止没删掉)
    const index2 = eventObjects.findIndex((eo: EventObject) => eo.event === event && eo.key === key);
    if (index2 > -1) {
      // 递归删除.必须保证每次订阅时,上下文环境是最新的
      this.subscribe(event, key, func);
    } else {
      const subject = new Subject();
      const subscription = subject.subscribe({
        next: (v: any) => {
          func(v);
        }
      });
      const eventObject: EventObject = {
        event,
        key,
        subject,
        subscription
      };
      eventObjects.push(eventObject);
    }
  },
  /**
   * 取消事件订阅
   * @param event
   * @param key
   * @param func
   */
  unsubscribe(event: string, key: string) {
    const index = eventObjects.findIndex((e: EventObject) => e.event === event && e.key === key);
    if (index > -1) {
      eventObjects[index].subject.unsubscribe();
      eventObjects.splice(index, 1);
    }
    // 递归删除.必须保证每次订阅时,上下文环境是最新的
    const index2 = eventObjects.findIndex((e: EventObject) => e.event === event && e.key === key);
    if (index > -1) {
      this.unsubscribe(event, key);
    }
  },
  /**
   * @param {event} event - 事件名称
   * @param {any} param - 参数
   */
  publish(event: string, param: any) {
    const eos = eventObjects.filter((eo: any) => {
      if (eo.event === event) {
        return eo;
      }
    });
    if (eos != null) {
      eos.forEach((element: any) => {
        const subject = element.subject;
        subject.next(param);
      });
    }
  }
};
