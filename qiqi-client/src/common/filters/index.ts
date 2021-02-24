/**
 * 处理空数据，界面不报错
 * @param val
 */
export const defaultNull = (val: any) => {
    return val ? val : '';
};
