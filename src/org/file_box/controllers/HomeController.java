package org.file_box.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.file_box.utils.CommonUtils;
import org.file_box.models.UserModel;
 
@WebServlet(urlPatterns = { "/home"})
public class HomeController extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public HomeController() {
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
		HttpSession session = request.getSession();
        // Check User has logged on
		UserModel loginedUser =  CommonUtils.getLoginedUser(session);
		request.setAttribute("user", loginedUser);
	    switch (action) {
	    	case "about":
	    		actionAbout(request, response, loginedUser, false);
	    		break;
	    	case "contact":
	    		actionContact(request, response, loginedUser, false);
	    		break;
	    	default:
	    			actionIndex(request, response, loginedUser, isPost);
	    		break;
	    }
	}
 
	private void actionIndex(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		// Forward to /WEB-INF/views/home/index.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/home/index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void actionAbout(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		// Forward to /WEB-INF/views/home/about.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/home/about.jsp");
		dispatcher.forward(request, response);
	}
	
	private void actionContact(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		// Forward to /WEB-INF/views/home/contact.jsp
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/home/contact.jsp");
		dispatcher.forward(request, response);
	}
}
