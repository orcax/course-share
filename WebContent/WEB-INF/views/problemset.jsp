<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<% 
  String path = request.getContextPath();
%>
<jsp:include page="layout-header.jsp" flush="true" />

<div id="cs-north-frame" class="row cs-frame-default">
  <div class="col-md-8">
    <div class="input-group">
      <span class="input-group-addon">输入题目关键词</span>
      <input type="text" id="ps-search-content" class="form-control" placeholder="关键词">
    </div>
  </div>
  <div class="col-md-4">
    <button type="button" id="ps-search" class="btn btn-primary">
      <span class="glyphicon glyphicon-search"></span> &nbsp查找题目
    </button>
    <button type="button" id="ps-paper" class="btn btn-primary">
      <span class="glyphicon glyphicon-print"></span> &nbsp自动出卷
    </button>
  </div>
</div> <!-- #problemset-header -->

<div id="cs-west-frame" class="cs-frame-default">
  <div class="form-group">
    <label for="knowledge">过滤知识点</label>
    <input type="text" class="form-control" id="ps-search-know" placeholder="知识点">
  </div>
  <hr>
  <%
  String types[][] = {
    {"type-all", "checked='checked'", "btn-default", "所有题型"},
    {"concept", "", "cs-frame-grey", "概念题"},
    {"blankfill", "", "cs-frame-blue", "填空题"},
    {"choice", "", "cs-frame-green", "选择题"},
    {"question", "", "cs-frame-yellow", "问答题"},
    {"integrate", "", "cs-frame-red", "综合题"}
  };
  for(String[] t : types) { %>
  <div class="checkbox">
    <label>
      <input type="checkbox" name="ps-type" id="<%=t[0] %>" <%=t[1] %>>
      <span class="btn cs-checkbox-text <%=t[2] %>"><%=t[3] %></span>
    </label>
  </div>
  <% } %>
  <hr>
  <%
  String diffs[][] = {
    {"diff-all", "checked='checked'", "btn-default", "所有难度"},
    {"1", "", "btn-primary", "难度：1级"},
    {"2", "", "btn-info", "难度：2级"},
    {"3", "", "btn-success", "难度：3级"},
    {"4", "", "btn-warning", "难度：4级"},
    {"5", "", "btn-danger", "难度：5级"}
  };
  for(String[] d : diffs) { %>
  <div class="checkbox">
    <label>
      <input type="checkbox" name="ps-diff" id="<%=d[0] %>" <%=d[1] %>>
      <span class="btn cs-checkbox-text <%=d[2] %>"><%=d[3] %></span>
    </label>
  </div>
  <% } %>
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
  <input type="hidden" id="ps-offset" value="0">
</div>

<script type="text/template" id="problem-tpl">
<div class="ps-row <@=typeCls @>" id="<@=id @>">
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
      <button id="<@=id @>" class="paper btn btn-success ps-btn-style">出题</button>
      <button id="<@=id @>" class="key btn btn-primary ps-btn-style">答案</button>
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