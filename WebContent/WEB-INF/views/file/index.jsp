<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*,org.file_box.models.FileModel,java.time.*,java.text.SimpleDateFormat" %>

<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="Files - FileBox" />
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
	<br/>
	<div class="row">
    <c:if test="${files != null}">
    	<c:if test="${count > 0}">
    		<table class="table table-striped table-bordered table-hover">
    			<thead>  
                	<tr>
                    	<th scope="col">#</th>
                    	<th scope="col">Title</th>
                    	<th scope="col">Description</th>
                    	<th scope="col">Created At</th>
                    	<th scope="col">Actions</th>  
                	</tr>
                </thead>
                <tbody>
                <c:forEach items="${files}" var="file" >
                    <tr>  
                        <td scope="row">${file.id}</td>
                        <td>${file.title}</td>
                        <td>${file.description}</td>
                        <td>
                        <%
        	 				Date date = Date.from( Instant.ofEpochSecond( ((FileModel)pageContext.getAttribute("file")).getCreatedAt() ) );
         					out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
      					%>
                        </td>
                        <td style="width: 100px;">
                            <a href="file?action=download&id=${file.id}" style="margin-right: 10px;">
                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-download" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
                                    <path fill-rule="evenodd" d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
                                </svg>
                            </a>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#model${file.id}" title="Delete File" >
                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                    <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                </svg>
                            </button>

                            <div class="modal fade" id="model${file.id}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Delete Confirmation</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            Are you sure want to delete this item?
                                        </div>
                                        <div class="modal-footer">
                                            <a href="file?action=delete&id=${file.id}" class="btn bg-danger mr-1">Delete</a>
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>  
                    </tr> 
                </c:forEach>
                <tbody>
            </table> 
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