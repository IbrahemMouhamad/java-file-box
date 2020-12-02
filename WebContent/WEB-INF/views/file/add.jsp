<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>

<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="Add File - FileBox" />
</jsp:include>

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
    <div class="col-md-1"></div>
    <div class="col-md-9">
        <div class="card border border-primary">
            <div class="card-header">
                Add new File
            </div>
            <form method="post" enctype="multipart/form-data">
                <div class="card-body">
                    <div class="form-group row">
                        <label for="title" class="col-sm-2 col-form-label">Title*:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" required name="title" />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="description" class="col-sm-2 col-form-label">Description*:</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" required name="description" ></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="file" class="col-sm-2 col-form-label">File*:</label>
                        <div class="col-sm-10">
                            <input type="file" class="form-control" required name="file" />
                        </div>
                    </div>
                </div>
                <div class="card-footer pull-right">
                    <button class="btn btn-primary" type="submit">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>