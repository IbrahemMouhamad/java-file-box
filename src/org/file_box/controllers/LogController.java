package org.file_box.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.file_box.models.ActionLogModel;
import org.file_box.models.UserModel;
import org.file_box.utils.CommonUtils;
import org.file_box.utils.DBUtils;
 
@WebServlet(urlPatterns = { "/log"})
public class LogController extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public LogController() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		   throws ServletException, IOException {
	   actionIndex(request, response);
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   doGet(request, response);
   }
 
	private void actionIndex(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		// get user
    	HttpSession session = request.getSession();
    	// Check User has logged on
    	UserModel loginedUser =  CommonUtils.getLoginedUser(session);
		// if not login => redirect to login page
        if(loginedUser == null) response.sendRedirect(request.getContextPath()+ "/admin?action=login");
        else {
        	// get the db connection
        	Connection connection = CommonUtils.getStoredConnection(request);
        	String errorString = null;
        	List<ActionLogModel> list = null;
        	// search for all files match the search
        	try {
        		list = DBUtils.queryLog(connection);
        	} catch (SQLException e) {
        		errorString = e.getMessage();
        	}
        	String action = ( request.getParameter("action") != null ) ? request.getParameter("action"):"";
        	// Store info in request attribute, before forward to views
        	request.setAttribute("errorString", errorString);
        	request.setAttribute("logs", list);
        	request.setAttribute("count", list.size());
        	request.setAttribute("user", loginedUser);
        	// Forward to /WEB-INF/views/log/index.jsp
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/log/index.jsp");
        	dispatcher.forward(request, response);
        }
	}
	
}
