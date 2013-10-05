<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%
  String path = request.getContextPath();
  LibType libType = (LibType) request.getAttribute("libType");
  String jsName = libType.toString().toLowerCase();
%>  
    </div> <!-- #cs-body-content -->
  </div> <!-- #cs-body -->
  
  <script src="//code.jquery.com/jquery.min.js"></script>
  <script src="<%=path%>/js/lib/bootstrap.min.js"></script>
  <script src="<%=path%>/js/lib/scrollpagination.js"></script>
  <script src="<%=path%>/js/jq-<%=jsName %>.js"></script>  
</body>
</html>
