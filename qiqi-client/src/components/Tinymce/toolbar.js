// Here is a list of the toolbar
// Detail list see https://www.tinymce.com/docs/advanced/editor-control-identifiers/#toolbarcontrols
// 'searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'
let str = 'fullscreen '; // 全屏
str += 'bold '; // 粗体
str += 'italic '; // 斜体
str += 'underline '; // 下划线
str += 'strikethrough '; // 删除线
str += 'alignleft aligncenter alignright '; // 居左，居中，居右
str += 'outdent indent '; // 减少缩进，增加缩进
str += 'undo redo '; // 撤销，恢复
str += 'removeformat '; // 清除格式
str += 'subscript superscript '; // 上标下标
str += 'forecolor backcolor '; // 字体颜色与背景颜色
str += 'preview '; // 预览
str += 'table '; // 表格
str += 'template '; // 内容模板
str += 'letterspacing '; // 文字间距
const toolbar = [str]

export default toolbar
