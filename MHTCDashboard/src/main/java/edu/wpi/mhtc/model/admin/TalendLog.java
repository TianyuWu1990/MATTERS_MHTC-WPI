/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.model.admin;

/**
 * Model for Talend Pipeline logs
 * @author Alex Fortier
 *
 */
public class TalendLog {

	private int id;
	private String moment;
	private String job;
	private String message;
	private int code;
	private int priority;
	private String origin;
	private int logCount;
	
	/**
	 * Constructor for use when getting object from DB
	 * @param id
	 * @param moment
	 * @param job
	 * @param message
	 * @param code
	 * @param priority
	 * @param origin
	 */
	public TalendLog(int id, String moment, String job, String message,
			int code, int priority, String origin) {
		this.id = id;
		this.moment = moment;
		this.job = job;
		this.message = message;
		this.code = code;
		this.priority = priority;
		this.origin = origin;
	}
	
	/**
	 * Constructor for use when saving new log
	 * @param moment
	 * @param job
	 * @param message
	 * @param code
	 * @param priority
	 * @param origin
	 */
	public TalendLog(String moment, String job, String message, int code,
			int priority, String origin) {
		this.id = 0;
		this.moment = moment;
		this.job = job;
		this.message = message;
		this.code = code;
		this.priority = priority;
		this.origin = origin;
	}

	/**
	 * Blank constructor, used for summary
	 */
	public TalendLog() {}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the moment
	 */
	public String getMoment() {
		return moment;
	}
	/**
	 * @param moment the moment to set
	 */
	public void setMoment(String moment) {
		this.moment = moment;
	}
	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}
	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the logCount
	 */
	public int getLogCount() {
		return logCount;
	}

	/**
	 * @param logCount the logCount to set
	 */
	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}
	
	
}
