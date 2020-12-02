<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>

<html lang="en">
<head>
    <meta charset="utf-8" />    
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${param.title}</title>
        
    <!-- CSS -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

	<!-- jQuery and JS bundle w/ Popper.js -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    <style><%@include file="/css/style.css"%></style>
</head>
<body>
	<header>
       <nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
            <div class="container">
                <a href='/file_box' class="navbar-brand">FileBox</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="navbar-collapse collapse d-sm-inline-flex flex-sm-row-reverse">
                    <ul class="navbar-nav flex-grow-1">
                    	<c:if test="${user == null}">
                        	<li class="nav-item">
                            	<a href='/file_box/home' class="nav-link text-dark">Home</a>
                        	</li>
                        </c:if>
                        <c:if test="${user != null}">
                        	<li class="nav-item">
                            	<a href='/file_box/file' class="nav-link text-dark">Files</a>
                        	</li>
                        </c:if>
                        <li class="nav-item">
                            <a href='/file_box/file?action=add' class="nav-link text-dark">Add File</a>
                        </li>
                        <c:if test="${user != null}">
                        	<li class="nav-item">
                            	<a href='/file_box/log' class="nav-link text-dark">Logs</a>
                        	</li>
                        </c:if>
                        <li class="nav-item">
                            <a href='/file_box/home?action=about' class="nav-link text-dark">About</a>
                        </li>
                        <li class="nav-item">
                            <a href='/file_box/home?action=contact' class="nav-link text-dark">Contact</a>
                        </li>
                        <c:if test="${user == null}">
                        	<li class="nav-item">
                            	<a href='admin' class="nav-link text-dark">Management</a>
                        	</li>
                        </c:if>
                        <c:if test="${user != null}">
                        	<li class="nav-item">
                            	<a href='admin?action=logout' class="nav-link text-dark">Logout (User: ${user.userName})</a>
                        	</li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <main role="main" class="container pb-3">