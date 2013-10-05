<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%@ page import="org.tjsse.courseshare.util.Const"%>
<%
  String path = request.getContextPath();
  LibType libType = (LibType) request.getAttribute("libType");
  String pageTitle = Const.TITLE + " —— " + libType.getName();
  String problemsetClass = "", subjectClass = "", flashClass = "", imageClass = "";
  switch(libType) {
  case PROBLEMSET:
    problemsetClass = "active";
    break;
  case SUBJECT:
    subjectClass = "active";
    break;
  case FLASH:
    flashClass = "active";
    break;
  case IMAGE:
    imageClass = "active";
    break;
  }
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
  <link href="<%=path %>/css/layout.css" rel="stylesheet">
  <!--[if lte IE 6]>
    <linkhref="<%=path %>/css/bootstrap-ie6.css" rel="stylesheet" >
  <![endif]-->
  <!--[if lte IE 7]>
    <link href="<%=path %>/css/ie.css" rel="stylesheet">
  <![endif]-->
  <!--[if lte IE 6]>
    <script type="text/javascript" src="js/lib/bootstrap-ie.js"></script>
  <![endif]-->
  <!--[if lt IE 9]>
    <script src="<%=path %>/js/assets/js/html5shiv.js"></script>
    <script src="<%=path %>/js/assets/js/respond.min.js"></script>
  <![endif]-->
  <title><%=pageTitle %></title>
</head>
<body>
  <script>
    var root = '<%=path %>/';
  </script>
  <div id="cs-header" class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a id="cs-header-brand" class="navbar-brand" href="#">
          <span class="glyphicon glyphicon-home"></span> &nbsp&nbsp<%=pageTitle %>
        </a>
      </div>
      <div id="cs-header-collapse" class="collapse navbar-collapse navbar-ex1-collapse pull-right">
        <ul id="cs-header-nav" class="nav navbar-nav">
          <li class="<%=imageClass %>">
            <a href="<%=path%>/image">
              <span class="glyphicon glyphicon-picture"></span> 素材库
            </a>
          </li>
          <li class="<%=flashClass %>">
            <a href="<%=path %>/flash">
              <span class="glyphicon glyphicon-film"></span> 动画库
            </a>
          </li> 
          <li class="<%=subjectClass %>">
            <a href="<%=path%>/subject">
              <span class="glyphicon glyphicon-list-alt"></span> 专题库
            </a>
          </li>
          <li class="<%=problemsetClass %>">
            <a href="<%=path%>/problem">
              <span class="glyphicon glyphicon-book"></span> 题库
            </a>
          </li>
        </ul> <!-- #cs-header-navbar -->
      </div>
    </div>
  </div> <!-- #cs-header -->
  <div id="cs-body">
    <div id="cs-body-content" class="container">