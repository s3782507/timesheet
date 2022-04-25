package com.timesheet.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "user")
public class User implements Serializable {
	
	@DynamoDBHashKey (attributeName = "userId")
	@DynamoDBAutoGeneratedKey
	private String userId;
	
	
	@DynamoDBAttribute
	private String fullname;
	
	@DynamoDBAttribute
	private String email;
	
	@DynamoDBAttribute
	private String password;
	
	@DynamoDBAttribute
	private List<Timesheet> timesheetList;
	
	
	public void setUserId(String userId) {
	    this.userId = userId;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}
	
	public void setPassword(String password) {
	    this.password = password;
	}

	public void setFullname(String fullname) {
	    this.fullname = fullname;
	}
	
	public void setTimesheetList(List<Timesheet> timesheetList) {
		this.timesheetList = timesheetList;
	}
	
	public String getUserId() {
	    return userId;
	}
	
	public String getEmail() {
	    return email;
	}
	
	public String getPassword() {
	    return password;
	}

	public String getFullname() {
	    return fullname;
	}
	
	public List<Timesheet> getTimesheetList() {
		return timesheetList;
	}
	
	public void addTimesheet(Timesheet timesheet) {
	    timesheetList.add(timesheet);
	}
}
