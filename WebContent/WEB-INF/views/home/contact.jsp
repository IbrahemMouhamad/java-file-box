<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="Contact - FileBox" />
</jsp:include>

<div class="site-contact">
    <div class="row">
        <div class="col-md-6">
            <h3>Contact Info:</h3>
            <hr/>
                <p>You can contact the author using:</p>
            <hr/>
            <ul style="list-style-type: disclosure-closed;">
                <li><i class='glyphicon glyphicon-envelope'></i> <a href="mailto: ibragim1@tpu.ru">ibragim1@tpu.ru</a> </li>
                <li><i class='glyphicon glyphicon-phone'></i> +7 (913) 119-76-71 </li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>
