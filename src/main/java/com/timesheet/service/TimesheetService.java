package com.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timesheet.model.User;
import com.timesheet.repository.TimesheetRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TimesheetService {
	
	@Autowired
	private TimesheetRepository repository;
	
	public void registerUserService(User user) {
		repository.addAndUpdateUser(user);
	}
	public List<User> loginUserService(String email, String password) {
		List<User> user = repository.findUserByEmail(email, password);
		return user;
	}
	
	public User viewTimesheetService(String email, String password) {
		List<User> user = repository.findUserByEmail(email, password);
		return user.get(0);

	}
	
	public void createAndUpdateTimesheetService(User user) {
		repository.addAndUpdateUser(user);
	}


}
