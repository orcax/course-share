<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.tjsse.courseshare.util.LibType"%>
<%
  String path = request.getContextPath();
  LibType libType = (LibType) request.getAttribute("libType");
  String jsName = libType.toString().toLowerCase();
%>  

    </div> <!-- #cs-body-content -->
  </div> <!-- #cs-body -->
  
<!-- Javascript Library -->  
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="//code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//underscorejs.org/underscore-min.js"></script>
<script>
var ROOT = '<%=path %>/';

_.templateSettings = {
  interpolate: /\<\@\=(.+?)\@\>/gim,
  evaluate: /\<\@(.+?)\@\>/gim,
  escape: /\<\@\-(.+?)\@\>/gim
};
</script>
<script src="<%=path%>/js/lib/scrollpagination.js"></script>
<script src="<%=path%>/js/jq-<%=jsName %>.js"></script>  

</body>
</html>
