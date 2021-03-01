// Any plugins you want to use has to be imported
// Detail plugins list see https://www.tinymce.com/docs/plugins/
// Custom builds see https://www.tinymce.com/download/custom-builds/
// image imagetools textcolor preview searchreplace autolink directionality
// visualblocks visualchars fullscreen link media template code codesample table
// charmap hr pagebreak nonbreaking anchor insertdatetime advlist lists wordcount textpattern
// help emoticons autosave
// import './letterspacing.min.js';
let str = 'image imagetools '; // 图片
str += 'textcolor '; // 字体颜色
str += 'preview fullscreen '; // 预览与全屏
str += 'table '; // 表格插件
str += 'emoticons '; // 表情插件
str += 'template '; // 内容模板
str += 'lists '; // 导入word 插件
str += 'letterspacing '; // 文字间距
const plugins = [str];

export default plugins;
