<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp">
	<jsp:param name="title" value="About - FileBox" />
</jsp:include>

<div class="site-about">
    <div class="row">
        <div class="col-md-6">
            <h3>What is it?</h3>
            <hr/>
            <p>Simple web application to add files with their descriptions, search by keywords and download files.</p>
            <p>Hosted on Amazon Web Services (AWS) using Apache2 web server and MariaDB (MySql) database server.</p>
            <h3>Features:</h3>
            <hr/>
            <ul style="list-style-type: decimal;">
                <li>'Add File' page, which allows the users to add their files with titles and descriptions.</li>
                <li>'Home' page, which allows users to search for files, depending on their descriptions, display the results, and download them.</li>
                <li>
                    'Management pages for administrators, which provides the admin with the following:
                    <ul style="list-style-type: disclosure-closed;">
                        <li>list file page, where the admin can explore all files, and delete unnecessary ones.</li>
                        <li>Add file page, to add new files.</li>
                        <li>Log page to display users action.</li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="col-md-6">
            <h3>About the author</h3>
            <hr/>
            <p>Name: <strong>Ibrahem Mouhamad </strong></p>
            <p><strong>Master student at Tomsk Polytechnic University <a href="https://tpu.ru/" target="_blank">TPU</a></strong></p>
        </div>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>