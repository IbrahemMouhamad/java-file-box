<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="Home - FileBox" />
</jsp:include>

<div class="site-index">

    <div class="jumbotron">
        <h1>File Box</h1>

        <form method="POST" action="file?action=search">
            <div class="form-group">
                <input name="q" class="form-control" aria-label="Search" placeholder="Search" required />
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Search</button>
                <button type="reset" class="btn btn-outline-secondary">Reset</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>