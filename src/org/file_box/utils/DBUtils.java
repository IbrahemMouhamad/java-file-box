package org.file_box.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.file_box.models.UserModel;
import org.file_box.models.FileModel;
import org.file_box.models.ActionLogModel;

public class DBUtils {
	
	public static UserModel findUser(Connection conn,
            String userName, String password) throws SQLException {
 
		// select user from db using user name and password
        String sql = "Select a.username, a.password from user a "
                + " where a.username = ? and a.password= ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            UserModel user = new UserModel();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }
 
    public static UserModel findUser(Connection conn, String userName) throws SQLException {
		// select user from db using user name
        String sql = "Select a.User_Name, a.Password from user a "
                + " where a.username = ? ";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
 
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String password = rs.getString("password");
            UserModel user = new UserModel();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }
 
    // get all files as list
    public static List<FileModel> queryFile(Connection conn) throws SQLException {
    	// get all files
        String sql = "Select f.id, f.title, f.path, f.description, f.created_at from file f";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // execute the query
        ResultSet rs = pstm.executeQuery();
        List<FileModel> list = new ArrayList<FileModel>();
        // add all results to the list
        while (rs.next()) {
            FileModel file = new FileModel(
            		rs.getInt("id"), rs.getString("title"), rs.getString("path"), rs.getString("description") 
            );
            file.setCreatedAt(rs.getInt("created_at"));
            list.add(file);
        }
        return list;
    }
    
    // get files based on search query
    public static List<FileModel> searchFile(Connection conn, String query) throws SQLException {
    	// search all files by description
        String sql = "Select f.id, f.title, f.path, f.description, f.created_at from file f where description like ?";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // add parameters
        pstm.setString(1, '%'+query+'%');
        // execute the query
        ResultSet rs = pstm.executeQuery();
        List<FileModel> list = new ArrayList<FileModel>();
        // add all results to the list
        while (rs.next()) {
            FileModel file = new FileModel(
            		rs.getInt("id"), rs.getString("title"), rs.getString("path"), rs.getString("description") 
            );
            file.setCreatedAt(rs.getInt("created_at"));
            list.add(file);
        }
        return list;
    }
    // get file by id
    public static FileModel getFileModel(Connection conn, String id) throws SQLException {
    	// search  files by id
        String sql = "Select f.id, f.title, f.path, f.description, f.created_at from file f where id=?";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // add parameters
        pstm.setString(1, id);
        // execute the query
        ResultSet rs = pstm.executeQuery();
        List<FileModel> list = new ArrayList<FileModel>();
        // add all results to the list
        while (rs.next()) {
            FileModel file = new FileModel(
            		rs.getInt("id"), rs.getString("title"), rs.getString("path"), rs.getString("description") 
            );
            file.setCreatedAt(rs.getInt("created_at"));
            list.add(file);
        }
        // return only one file or nothing
        return list.size() == 1 ? list.get(0):null;
    }
    //insert file in database
    public static void insertFile(Connection conn, FileModel file) throws SQLException {
    	// insert new file
        String sql = "Insert into file(title, path,description, created_at) values (?,?,?,?)";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // add parameters
        pstm.setString(1, file.getTitle());
        pstm.setString(2, file.getPath());
        pstm.setString(3, file.getDescription());
        pstm.setLong(4, file.getCreatedAt());
        // execute the query
        pstm.executeUpdate();
    }
    // delete file by id
    public static void deleteFile(Connection conn, String id) throws SQLException {
    	// delete file by id
        String sql = "Delete From file where id= ?";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // add parameters
        pstm.setString(1, id);
         // execute the query
        pstm.executeUpdate();
    }
    // get all logs
    public static List<ActionLogModel> queryLog(Connection conn) throws SQLException {
    	// get all logs
        String sql = "Select l.id, l.user , l.user_remote, l.time , l.message  from actionlog l";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // execute the query
        ResultSet rs = pstm.executeQuery();
        List<ActionLogModel> list = new ArrayList<ActionLogModel>();
        // add all results to the list
        while (rs.next()) {
        	ActionLogModel log = new ActionLogModel(
            		rs.getInt("id"), rs.getString("user"), rs.getString("user_remote"), rs.getString("message") 
            );
        	log.setTime(rs.getInt("time"));
            list.add(log);
        }
        return list;
    }
    //insert log in database
    public static void insertLog(Connection conn, ActionLogModel log) throws SQLException {
    	// insert new file
        String sql = "Insert into actionlog(user, user_remote, time, message) values (?,?,?,?)";
        // build the query
        PreparedStatement pstm = conn.prepareStatement(sql);
        // add parameters
        pstm.setString(1, log.getUserame());
        pstm.setString(2, log.getRemoteIP());
        pstm.setLong(3, log.getTime());
        pstm.setString(4, log.getMessage());
        // execute the query
        pstm.executeUpdate();
    }
}
