<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*,org.file_box.models.FileModel,java.time.*,java.text.SimpleDateFormat" %>

<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="Search Results - FileBox" />
</jsp:include>

<div class="file-index">
	<c:if test="${errorString != null}">
    	<div class="row">
			<div class="col-md-1"></div>
    		<div class="col-sm-9">
        		<div class="alert alert-danger" role="alert">
            		<p>${errorString}</p>
        		</div>
    		</div>
		</div>   
	</c:if>
	<div class="row">
    	<div class="col-md-12">
        	<form method="POST" class="row">
            	<div class="form-group col-md-7">
                	<input name="q" class="form-control" aria-label="Search" placeholder="Search" required=true />
            	</div>
            	<div class="form-group col-md-4">
                	<button type="submit" class="btn btn-primary">Search</button>
            	</div>
        	</form>
    	</div>
	</div>
	<div class="row">
    <c:if test="${files != null}">
    	<c:if test="${count > 0}">
    		<c:forEach items="${files}" var="file" >
    			<div class="col-md-8 file-item">
                	<a href="file?action=download&id=${file.id}" >
                    	<h3>
                        	<span>
                        		${file.title}
                        	</span>
                    	</h3>
                	</a>
                	<p>${file.description}</p>
                	<small>
                	<%
        	 				Date date = Date.from( Instant.ofEpochSecond( ((FileModel)pageContext.getAttribute("file")).getCreatedAt() ) );
         					out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
      				%>
                	</small>
            	</div>
    		</c:forEach>
    	</c:if>
    	<c:if test="${count == 0}">
    		<div class="col-md-8">
            	<h3>No results found!</h3>
        	</div>
    	</c:if>
    </c:if>
</div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>