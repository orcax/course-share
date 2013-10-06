<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<% 
  String path = request.getContextPath(); 
%>
<jsp:include page="layout-header.jsp" flush="true" />

<div id="cs-north-frame" class="row cs-frame-default">
  <div class="col-md-8">
    <div class="input-group">
      <span class="input-group-addon">输入题目关键词</span>
      <input type="text" class="form-control" placeholder="关键词">
    </div>
  </div>
  <div class="col-md-4">
    <button type="button" class="btn btn-primary">
      <span class="glyphicon glyphicon-search"></span> &nbsp&nbsp查找题目
    </button>
  </div>
</div> <!-- #problemset-header -->

<div id="cs-west-frame" class="cs-frame-default">
  <div class="form-group">
    <label for="knowledge">过滤知识点</label>
    <input type="text" class="form-control" id="knowledge" placeholder="知识点">
  </div>
  <hr />
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn btn-default cs-checkbox-text">所有题型</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn cs-frame-grey cs-checkbox-text">概念题</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn cs-frame-blue cs-checkbox-text">填空题</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn cs-frame-green cs-checkbox-text">选择题</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox"> 
      <span class="btn cs-frame-yellow cs-checkbox-text">问答题</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox"> 
      <span class="btn cs-frame-red cs-checkbox-text">综合题</span>
    </label>
  </div>
  <hr />
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn btn-default cs-checkbox-text">所有难度</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn btn-primary cs-checkbox-text">难度：1级</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox"> 
      <span class="btn btn-info cs-checkbox-text">难度：2级</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox"> 
      <span class="btn btn-success cs-checkbox-text">难度：3级</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn btn-warning cs-checkbox-text">难度：4级</span>
    </label>
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox">
      <span class="btn btn-danger cs-checkbox-text">难度：5级</span>
    </label>
  </div>
</div> <!-- #cs-west-frame -->

<div id="cs-center-frame">
  <div class="ps-row cs-frame-default">
    <div class="ps-col ps-title ps-type">题型</div>
    <div class="ps-col ps-title ps-diff">难度</div> 
    <div class="ps-col ps-title ps-content">题目</div>   
    <div class="ps-col ps-title ps-know">知识点</div>
  </div>
  <div id="problemset-list"></div>
  <div id="problemset-loading" class="loading">Loading ......</div>
</div>

<script type="text/template" id="problem-tpl">
<div class="ps-row <@=typeCls @>">
  <div class="ps-problem">
    <div class="ps-col ps-type">
      <span class="btn ps-type-style cs-style-grey2"><@=type @></span>
    </div>
    <div class="ps-col ps-diff">
      <span class="btn ps-diff-style <@=diffCls @>"><@=diff @></span>
    </div>
    <div class="ps-col ps-content">
      <@=content @>
    </div>
    <div class="ps-col ps-know">
      <@=know @>
    </div>
    <div class="ps-col" style="float:right;">
      <button id="<@=id @>" class="key btn btn-danger ps-btn-style">答案</button>
      <button id="<@=id @>" class="paper btn btn-warning ps-btn-style">出题</button>
    </div>
  </div>
  <div id="<@=id @>" class="ps-key">
    <div class="ps-key-left">
              【答案】 <span class="glyphicon glyphicon-hand-right"></span> 
    </div>
    <div class="ps-key-right"><@=key @></div>
  </div>
</div>
</script>

<jsp:include page="layout-footer.jsp" flush="true" />