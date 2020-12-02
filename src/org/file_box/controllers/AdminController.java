package org.file_box.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.file_box.models.ActionLogModel;
import org.file_box.models.UserModel;
import org.file_box.utils.CommonUtils;
import org.file_box.utils.DBUtils;
 
@WebServlet(urlPatterns = { "/admin"})
public class AdminController extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public AdminController() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		   throws ServletException, IOException {
    	processRequest(request, response, false);
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   processRequest(request, response, true);
   }
   
	private void processRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost) 
			throws ServletException, IOException {
		String action = ( request.getParameter("action") != null ) ? request.getParameter("action"):"";
		UserModel loginedUser = getUser(request);
		request.setAttribute("user", loginedUser);
	    switch (action) {
	    	case "login":
	    		actionLogin(request, response, isPost);
	    		break;
	    	case "logs":
	    		actionLogs(request, response, loginedUser, false);
	    		break;
	    	case "logout":
	    		HttpSession session = request.getSession();
	            CommonUtils.deleteLoginedUser(session);
	            CommonUtils.deleteUserCookie(response);
	            request.removeAttribute("user");
	            actionLogin(request, response, isPost);
	    		break;
	    	default:
	    			actionIndex(request, response, loginedUser, isPost);
	    		break;
	    }
	}
 
	private void actionLogin(HttpServletRequest request, HttpServletResponse response, boolean isPost) 
		throws ServletException, IOException {
		if(isPost) {
			String userName = request.getParameter("username");
	        String rememberMeStr = request.getParameter("rememberMe");
	        boolean remember = "Y".equals(rememberMeStr);
	 
	        UserModel user = null;
	        boolean hasError = false;
	        String errorString = null;
	        
			// get hashed password
	        String password= getHash(request.getParameter("password").trim());
	        
	        Connection conn = CommonUtils.getStoredConnection(request);
	        try {
	        	// Find the user in the DB.
	            user = DBUtils.findUser(conn, userName, password);
	            if (user == null) {
	            	hasError = true;
	                errorString = "User Name or password invalid";
	            }
	        } catch (SQLException e) {
	                hasError = true;
	                errorString = e.getMessage();
	        }
	        // If error, forward to /WEB-INF/views/login.jsp
	        if (hasError) {
	            // Store information in request attribute, before forward.
	            request.setAttribute("errorString", errorString);
	            request.setAttribute("user", null);
	 
	            // Forward to /WEB-INF/views/login.jsp
	            RequestDispatcher dispatcher //
	                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/login.jsp");
	            dispatcher.forward(request, response);
	        }
	        // If no error
	        // Store user information in Session
	        // And redirect to userInfo page.
	        else {
	            HttpSession session = request.getSession();
	            CommonUtils.storeLoginedUser(session, user);
	 
	            // If user checked "Remember me".
	            if (remember) {
	            	CommonUtils.storeUserCookie(response, user);
	            }
	            // Else delete cookie.
	            else {
	            	CommonUtils.deleteUserCookie(response);
	            }
	
	            // Redirect to file index page.
	            response.sendRedirect(request.getContextPath() + "/file");
	        }
		}
		else {
			// Forward to /WEB-INF/views/admin/login.jsp
			RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/admin/login.jsp");
		    dispatcher.forward(request, response);
		}
	}
	
	private void actionLogs(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
        // if not login => redirect to login page
        if(loginedUser == null) actionLogin(request, response, false);
		// redirect to log index
		response.sendRedirect(request.getContextPath()+ "/log");
	}
	
		
	private void actionIndex(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
        // if not login => redirect to login page
        if(loginedUser == null) actionLogin(request, response, isPost);
		// redirect to file index
        else response.sendRedirect(request.getContextPath()+ "/file");
	}
	
	private UserModel getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
        // Check User has logged on
        return CommonUtils.getLoginedUser(session);
	}
	
	private String getHash(String str){
	   try {
	        byte[]  three = str.getBytes("UTF-8");
	        MessageDigest   md = MessageDigest.getInstance("MD5");
	        byte[] thedigest = md.digest(three);
	        StringBuilder buff = new StringBuilder();
	        for (byte b : thedigest) {
	          String conversion = Integer.toString(b & 0xFF,16);
	          while (conversion.length() < 2) {
	            conversion = "0" + conversion;
	          }
	          buff.append(conversion);
	        }
	        return buff.toString();
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    }
	   return "";
	}
}
