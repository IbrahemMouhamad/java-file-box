<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>

<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="Admin Login - FileBox" />
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
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="card border border-primary">
            <div class="card-header">
                Login
            </div>
            <form method="post">
                <div class="card-body">
                    <div class="form-group row">
                        <label for="title" class="col-sm-4 col-form-label">Username*:</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" required name="username" />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="description" class="col-sm-4 col-form-label">Password*:</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" required name="password" ></textarea>
                        </div>
                    </div>
                </div>
                <div class="card-footer pull-right">
                	<div class="form-group row">
                        <div class="col-sm-1">
                            <input type="checkbox" class="form-control" style="width: 15px;height: 20px;margin-top: calc(.375rem + 1px);" name="rememberMe" value= "Y" />
                        </div>
                        <label for="description" class="col-sm-10 col-form-label">Remember Me</label>
                    </div>
                    <button class="btn btn-primary" type="submit">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>