package org.file_box.models;

import java.time.Instant;
import java.sql.Connection;
import java.sql.SQLException;

import org.file_box.utils.DBUtils;

public class ActionLogModel {
	private int id;
	// user name who perform the action 
	private String userame;
	// user ip address
	private String remoteIP;
	// log message
	private String message;
	// log time
	private long time;
	
	public ActionLogModel () {}

	public ActionLogModel (int id, String userame, String remoteIP, String message) {
		this.id = id;
		this.userame = userame;
		this.remoteIP = remoteIP;
		this.message =  message;
	}
	
	public ActionLogModel (String userame, String remoteIP, String message) {
		this.userame = userame;
		this.remoteIP = remoteIP;
		this.message =  message;
		// get the current time stamp
		this.time = Instant.now().getEpochSecond();
	}
	
	public static void addLog(String userame, String remoteIP, String message, Connection conn) {
		ActionLogModel log = new ActionLogModel(userame, remoteIP, message);
		// search for all files match the search
	    try {
	    	DBUtils.insertLog(conn, log);
	    } catch (SQLException e) {
	    }
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserame() {
		return userame;
	}
	public void setUserame(String userame) {
		this.userame = userame;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
}
