package org.file_box.models;

import java.time.Instant;

public class FileModel {
	private int id;
	// file title
	private String title;
	// file's relative path in the file system
	private String path;
	// file description
	private String description;
	// file recored creation time
	private long createdAt;
	
	public FileModel () {}
	
	public FileModel (int id, String title, String path, String desc) {
		this.id = id;
		this.title = title;
		this.path = path;
		this.description = desc;
	}
	
	public FileModel (String title, String path, String desc) {
		this.title = title;
		this.path = path;
		this.description = desc;
		// get the current time stamp
		this.createdAt = Instant.now().getEpochSecond();
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}
	
	public void setCreatedAt(long t) {
		this.createdAt = t;
	}

	public long getCreatedAt() {
		return createdAt;
	}
}
