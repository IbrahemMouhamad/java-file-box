package org.file_box.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.file_box.models.ActionLogModel;
import org.file_box.models.FileModel;
import org.file_box.models.UserModel;
import org.file_box.utils.CommonUtils;
import org.file_box.utils.DBUtils;
 

@WebServlet(urlPatterns = { "/file"})
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 1024, 
		maxRequestSize = 1024 * 1024 * 1024
)
public class FileController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final String UPLOAD_DIRECTORY = "files"; 
 
   public FileController() {
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
		// get user
		UserModel loginedUser = getUser(request);
		request.setAttribute("user", loginedUser);
	    switch (action) {
	    	case "add":
	    		actionAdd(request, response, loginedUser, isPost);
	    		break;
	    	case "search":
	    		actionSearch(request, response, loginedUser, isPost);
	    		break;
	    	case "download":
	    		actionDownload(request, response, loginedUser, false);
	    		break;
	    	case "delete":
	    		actionDelete(request, response, loginedUser, false);
	    		break;
	    	default:
	    			actionIndex(request, response, loginedUser, isPost);
	    		break;
	    }
	}
 
	private void actionIndex(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		// if not login => redirect to login page
        if(loginedUser == null) response.sendRedirect(request.getContextPath()+ "/admin?action=login");
        else {
        	// get the db connection
        	Connection connection = CommonUtils.getStoredConnection(request);
        	String errorString = null;
        	List<FileModel> list = null;
        	// search for all files match the search
        	try {
        		list = DBUtils.queryFile(connection);
        	} catch (SQLException e) {
        		errorString = e.getMessage();
        	}
        	// Store info in request attribute, before forward to views
        	request.setAttribute("errorString", errorString);
        	request.setAttribute("files", list);
        	request.setAttribute("count", list.size());
        	// Forward to /WEB-INF/views/file/index.jsp
        	RequestDispatcher dispatcher = request.getServletContext()
        			.getRequestDispatcher("/WEB-INF/views/file/index.jsp");
        	dispatcher.forward(request, response);
        }
	}
	
	private void actionAdd(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		if(isPost) {
			// get the db connection
			Connection connection = CommonUtils.getStoredConnection(request);
			// create file model
			FileModel newFile = new FileModel(
					(String) request.getParameter("title").trim(), "", 
					(String) request.getParameter("description").trim() );
			// absolute path in the file system
			String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
			File uploadDir = new File(uploadPath);
			// if upload folder not exist, create it
			if (!uploadDir.exists()) uploadDir.mkdir();
			// save the file
			String fileName;
			for (Part part : request.getParts()) {
				fileName = getFileName(part);
				if(fileName != null) {
					// add time stamp so always will get unique file name
					fileName = newFile.getCreatedAt() + fileName;
					// set path
					newFile.setPath(File.separator + UPLOAD_DIRECTORY + File.separator + fileName);
					// write to hard disk
					part.write(uploadPath + File.separator + fileName);
				}
			}
			// try add to db
			String errorString = null;
			try {
                DBUtils.insertFile(connection, newFile);
            } catch (SQLException e) {
                errorString = e.getMessage();
            }
			
			// Store information to request attribute, before forward to views.
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("file", newFile);
	        // If error, forward to add page.
	        if (errorString != null) {
	            RequestDispatcher dispatcher = request.getServletContext()
	                    .getRequestDispatcher("/WEB-INF/views/file/add.jsp");
	            dispatcher.forward(request, response);
	        }
	        // If everything nice.
	        // Redirect to the file listing page.
	        else {
	        	// add log file
	    	    ActionLogModel.addLog( 
	    	    		(loginedUser == null) ? "":loginedUser.getUserName(), 
	    	    		request.getRemoteAddr(), "new file added (title: " + newFile.getTitle() + ")", connection);
	    	    response.sendRedirect(request.getContextPath()+ "/file?action=add");
	        }
		}
		else {
			// Forward to /WEB-INF/views/file/add.jsp
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/file/add.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void actionSearch(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		if(isPost) {
			// get the db connection
			Connection connection = CommonUtils.getStoredConnection(request);
			String errorString = null;
	        List<FileModel> list = null;
	        // search for all files match the search
	        try {
	            list = DBUtils.searchFile(connection, (String) request.getParameter("q").trim());
	        } catch (SQLException e) {
	            errorString = e.getMessage();
	        }
	        // Store info in request attribute, before forward to views
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("files", list);
	        request.setAttribute("count", list.size());
		}
	    // Forward to /WEB-INF/views/file/results.jsp
		RequestDispatcher dispatcher = request.getServletContext()
			.getRequestDispatcher("/WEB-INF/views/file/results.jsp");
	    dispatcher.forward(request, response);
	}
	
	private void actionDownload(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
		throws ServletException, IOException {
		//	get the db connection
		Connection connection = CommonUtils.getStoredConnection(request);
		String errorString = null;
		FileModel requiredfile = null;
		// get the file by id
		try {
			requiredfile = DBUtils.getFileModel(connection, (String) request.getParameter("id").trim());
			// return error if the file not found
			if (requiredfile==null) errorString = "Invalid file id";
		} catch (SQLException e) {
			errorString = e.getMessage();
		}
		// Store error in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		// if there are an error show it
		if (errorString != null) {
			response.sendRedirect(request.getHeader("Referer"));
        }
        // If everything nice.
        // send the file content.
        else {
        	// absolute path in the file system
        	String filePath = getServletContext().getRealPath("") + requiredfile.getPath();
        	File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);
             
            // obtains ServletContext
            ServletContext context = getServletContext();
             
            // gets MIME type of the file
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {        
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
             
            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());
             
            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
             
            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();
             
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
             
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            
            // add log file
    	    ActionLogModel.addLog( 
    	    		(loginedUser == null) ? "":loginedUser.getUserName(), 
    	    		request.getRemoteAddr(), "download file (title: " + requiredfile.getTitle() + ")", connection);
    	    
            inStream.close();
            outStream.close();      
        }
	}
	
	
	private void actionDelete(HttpServletRequest request, HttpServletResponse response, UserModel loginedUser, boolean isPost) 
			throws ServletException, IOException {
			// 	if not login => redirect to login page
        	if(loginedUser == null) response.sendRedirect(request.getContextPath()+ "/admin?action=login");
        	else {
        		// get the db connection
        		Connection connection = CommonUtils.getStoredConnection(request);
        		String errorString = null;
        		FileModel requiredfile = null;
        		// delete the file by id
        		try {
        			requiredfile = DBUtils.getFileModel(connection, (String) request.getParameter("id").trim());
        			// return error if the file not found
        			if (requiredfile==null) errorString = "Invalid file id";
        			// delete from database
        			else DBUtils.deleteFile(connection, (String) request.getParameter("id").trim());
        		} catch (SQLException e) {
        			errorString = e.getMessage();
        		}
        		// if there are an error show it
        		if (errorString != null) {
        			// Store error in request attribute, before forward to views
        			request.setAttribute("errorString", errorString);
        			response.sendRedirect(request.getHeader("Referer"));
        		}
        		// If everything nice.
        		// delete the file from file system.
        		else {
        			// absolute path in the file system
        			String filePath = getServletContext().getRealPath("") + requiredfile.getPath();
        			File deletedFile = new File(filePath);
        			if (deletedFile.delete()) {
        				// add log file
        				ActionLogModel.addLog( 
	        	    		(loginedUser == null) ? "":loginedUser.getUserName(), 
	        	    		request.getRemoteAddr(), "delete file (title: " + requiredfile.getTitle() + ")", connection);
        				response.sendRedirect(request.getHeader("Referer"));
        			}
        			else {
        				errorString = "file content not deleted";
        				// Store error in request attribute, before forward to views
        				request.setAttribute("errorString", errorString);
        				response.sendRedirect(request.getHeader("Referer"));
        			}
        		}
        	}
	}
	
	
	private String getFileName(Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename"))
	            return content.substring(content.indexOf("=") + 2, content.length() - 1);
	        }
	    return null;
	}
	
	private UserModel getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
        // Check User has logged on
        return CommonUtils.getLoginedUser(session);
	}
}
