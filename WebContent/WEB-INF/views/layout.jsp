<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<%
  String path = request.getContextPath();
  String pageTitle = (String) request.getAttribute("pageTitle");
  if (pageTitle == null)
    pageTitle = "";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>计算机系统结构资源——${pageTitle }</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/ext-all.css" />
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
    widht: 100%;
    height: 100%;
}

body {
  padding-top: 52px;
}

.center {
	width: 960px;
	margin: 0 auto;
}

.mystyle_all {
display: inline;
padding: .2em .6em .3em;
font-weight: bold;
line-height: 1;
color: #ffffff;
text-align: center;
white-space: nowrap;
vertical-align: baseline;
border-radius: .25em;
}

.mystyle_grey {
  background-color: #999999;
}

.mystyle_grey {
  background-color: #999;
}

.mystyle_blue{
  background-color: #428bca;
}

.mystyle_green{
  background-color: #5cb85c;
}

.mystyle_yellow{
  background-color: #f0ad4e;
}

.mystyle_red {
  background-color: #d9534f;
}

.mystyle_bluegreen {
  background-color: #5bc0de;
}

.mystyle_black {
  background-color: #222;
}

.header_shadow {
  -webkit-box-shadow: 0 2px 3px rgba(0,0,0,0.3);
  -moz-box-shadow: 0 2px 3px rgba(0,0,0,0.3);
  box-shadow: 0 2px 3px rgba(0,0,0,0.3);
}

.checkbox_white {
  color: white;
  font-weight: normal;
}

.checkbox_whitebold {
  color: white;
  font-weight: bold;
}

.button_yellow {
display: inline;
padding: .2em .6em .3em;
font-weight: bold;
line-height: 1;
color: #333;
cursor: pointer;
text-align: center;
white-space: nowrap;
vertical-align: baseline;
border-radius: .25em;
background-color: #fee94f;
border-color: #fa2;
}

.button_yellow:hover {
display: inline;
padding: .2em .6em .3em;
text-decoration: none;
font-weight: bold;
line-height: 1;
color: #333;
cursor: pointer;
text-align: center;
white-space: nowrap;
vertical-align: baseline;
border-radius: .25em;
background-color: #fd9a0f;
border-color: #fa2;
}

.link_color {
  color: #00802a;
}

</style>
<script>
var root = '<%=path%>/';
var pageTitle = '<%=pageTitle%>';
</script>
<script type="text/javascript" src="<%=path%>/js/lib/ext-all.js"></script>
<!--[if lt IE 9]>
<script src="<%=path%>/js/assets/js/html5shiv.js"></script>
<script src="<%=path%>/js/assets/js/respond.min.js"></script>
<![endif]-->
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top header_shadow" style="height:52px;border-radius:0;margin:0;">
    <div class="center">
      <div class="navbar-header">
        <p class="navbar-brand" style="color: white;">计算机系统结构资源——${pageTitle }</p>
      </div>
      <div class="collapse navbar-collapse navbar-ex1-collapse pull-right">
        <ul class="nav navbar-nav">
        <%if(pageTitle.equals("素材库")) {%>
          <li class="active"><a href="<%=path%>/image">素材库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/image">素材库</a></li>
        <%}%>
        <%if(pageTitle.equals("动画库")) {%>
          <li class="active"><a href="<%=path%>/flash">动画库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/flash">动画库</a></li>
        <%}%>
        <%if(pageTitle.equals("专题库")) {%>
          <li class="active"><a href="<%=path%>/subject">专题库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/subject">专题库</a></li>
        <%}%>
        <%if(pageTitle.equals("题库")) {%>
          <li class="active"><a href="<%=path%>/problemset">题库</a></li>
        <%} else {%>
          <li><a href="<%=path%>/problemset">题库</a></li>
        <%}%>
        </ul>
      </div>
    </div>
  </div>
  <div style="height: 100%; clear: both; background-color: #f7f7f7;">
    <div id="main-content" class="center" style="height:100%;">
    </div>
  </div>
  <script type="text/javascript" src="<%=path%>/js/assets/js/jquery.js"></script>
  <script type="text/javascript" src="<%=path%>/js/lib/bootstrap.min.js"></script>  
</body>
</html>