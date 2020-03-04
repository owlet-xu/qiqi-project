/**
 * 脚本用于解析百度搜索的结果
 * 作者：xuguoyuan
 */
var baiduRes = []; // 百度搜索结果
baiduApi = {
  /**
   * 递归dom的子dom,解析出百度搜索结果
   */
  recursiveDoms(doms, res) {
    doms.forEach(dom => {
      if (baiduApi.isSearchTitle(dom)) {
        res.title = dom.innerHTML;
      } else if (baiduApi.isSearchContent(dom)) {
        res.content =  dom.innerHTML;
      }
      if (dom.childNodes && dom.childNodes.length > 0) {
        baiduApi.recursiveDoms(dom.childNodes, res);
      }
    });
  },
  nextPage: function() {
    var pageA = document.querySelectorAll('#page>a');
    pageA[pageA.length - 1].click();
  },
  prePage: function() {
    var pageA = document.querySelectorAll('#page>a');
    pageA[0].click();
  },
  /**
   * 解析百度搜索标题和结果
   */
  getSearchContent() {
    var as = document.querySelectorAll('#content_left>div.c-container');
    baiduRes = [];
    as.forEach(dom => {
      var tc = {title: '', content: ''};
      baiduApi.recursiveDoms([dom], tc);
      baiduRes.push(tc);
    });
    return baiduRes;
  },
  /**
   * dom 是不是标题
   */
  isSearchTitle(dom) {
    if (dom.nodeName.toLowerCase() === 'a' && dom.parentElement.nodeName.toLowerCase() === 'h3') {
      return true;
    } else {
      return false;
    }
  },
  /**
   * dom是不是结果
   */
  isSearchContent(dom) {
    // console.log(dom, '---111');
    // console.log(dom.className, '---222');
    if (dom.nodeName.toLowerCase() === 'div' &&  dom.className.indexOf('c-abstract') > -1) {
      return true;
    } else {
      return false;
    }
  }
};
