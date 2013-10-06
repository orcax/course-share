<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%@ page import="org.tjsse.courseshare.util.Const"%>
<%
  String path = request.getContextPath();
  LibType libType = (LibType) request.getAttribute("libType");
  String pageTitle = Const.TITLE + " —— " + libType.getName();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><%=pageTitle %></title>
  
  <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
  <link href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.min.css" rel="stylesheet">
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
</head>
<body>
  <div id="cs-header" class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a id="cs-header-brand" class="navbar-brand" href="#">
          <span class="glyphicon glyphicon-home"></span> &nbsp&nbsp<%=pageTitle %>
        </a>
      </div>
      <div id="cs-header-collapse" class="collapse navbar-collapse navbar-ex1-collapse pull-right">
        <ul id="cs-header-nav" class="nav navbar-nav">
          <li class="<%=libType.isEqual(LibType.IMAGE, "active") %>">
            <a href="<%=path%>/image">
              <span class="glyphicon glyphicon-picture"></span> <%=LibType.IMAGE.getName() %>
            </a>
          </li>
          <li class="<%=libType.isEqual(LibType.FLASH, "active") %>">
            <a href="<%=path %>/flash">
              <span class="glyphicon glyphicon-film"></span> <%=LibType.FLASH.getName() %>
            </a>
          </li> 
          <li class="<%=libType.isEqual(LibType.SUBJECT, "active") %>">
            <a href="<%=path%>/subject">
              <span class="glyphicon glyphicon-list-alt"></span> <%=LibType.SUBJECT.getName() %>
            </a>
          </li>
          <li class="<%=libType.isEqual(LibType.PROBLEMSET, "active") %>">
            <a href="<%=path%>/problemset">
              <span class="glyphicon glyphicon-book"></span> <%=LibType.PROBLEMSET.getName() %>
            </a>
          </li>
        </ul> <!-- #cs-header-nav -->
      </div> <!-- #cs-header-collapse -->
    </div> <!-- .container -->
  </div> <!-- #cs-header -->
  
  <div id="cs-body">
    <div id="cs-body-content" class="container">