package com.timesheet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.timesheet.model.Timesheet;
import com.timesheet.model.User;
import com.timesheet.service.TimesheetService;



@Controller
public class TimesheetController {
	
	@Autowired
	private TimesheetService service;
	
	private User currentUser;
	
	
	@GetMapping("/")
	public String home() {
		if(currentUser == null)
			return "redirect:/login";
		return "home";
	}
	
	@GetMapping("/home")
	public String home(@RequestParam(value = "name", defaultValue = "User") String name) {
		if(currentUser == null)
			return "redirect:/login";
		return "home";
	}
	
	@GetMapping("/login")
	public String loginPage(@ModelAttribute User user) {
		return "login";
	}
	@PostMapping("/login")
	public String login(@ModelAttribute User user) {
		List<User> containValidUser = service.loginUserService(user.getEmail(), user.getPassword());
		if(!containValidUser.isEmpty()) {
			currentUser = containValidUser.get(0);
			return "redirect:/home";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/register")
	public String registerPage(@ModelAttribute User user) {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user) {
		service.registerUserService(user);	
		return "redirect:/login";
	}

	@GetMapping("/create-timesheet")
	public String createTimesheet(Model model) {
		if(currentUser == null)
			return "redirect:/login";
		
		Timesheet timesheet = new Timesheet();
		model.addAttribute("timesheet", timesheet);
		
		return "createtimesheet";
	}
	@PostMapping("/create-timesheet")
	public String saveTimesheet(@ModelAttribute("timesheet") Timesheet timesheet) {
		timesheet.setTimesheetId(UUID.randomUUID().toString());
		List<Timesheet> timesheetList = new ArrayList<>();
		if(currentUser.getTimesheetList() != null) {
			timesheetList = currentUser.getTimesheetList();
		}
		timesheetList.add(timesheet);
		
		currentUser.setTimesheetList(timesheetList);
		service.createAndUpdateTimesheetService(currentUser);
		return "redirect:/view-timesheet";
	}
	

	
	@GetMapping("/view-timesheet")
	public String viewTimesheet(Model model) {
		if(currentUser == null)
			return "redirect:/login";
		
		model.addAttribute("timesheetList", currentUser.getTimesheetList());
		return "viewtimesheet";
	
	}
	
	@GetMapping("/update-timesheet/{id}")
	public String updateTimesheetPage(@PathVariable String id, Model model) {
		if(currentUser == null)
			return "redirect:/login";
		model.addAttribute("timesheet", getTimehsheetById(currentUser, id));
		return "updatetimesheet";
	}
	
	@PostMapping("/update-timesheet/{id}")
	public String updateTimesheet(@PathVariable String id, @ModelAttribute("timesheet") Timesheet timesheet) {
		timesheet.setTimesheetId(id);
		List<Timesheet> timesheetList = removeTimehsheetById(currentUser, id);
		timesheetList.add(timesheet);
		
		currentUser.setTimesheetList(timesheetList);
		service.createAndUpdateTimesheetService(currentUser);
		return "redirect:/view-timesheet";
	}
	
	@GetMapping("/logout")
	public String logout() {
		if(currentUser == null) {
			return "redirect:/login";
		}else {
			currentUser = null;
			return "redirect:/login";
		}
	}
	
	
	private Timesheet getTimehsheetById(User user, String id) {	
		Timesheet timesheetById = null;
		for(Timesheet timesheet : user.getTimesheetList()) {
			if(timesheet.getTimesheetId().equals(id)) {
				timesheetById = timesheet;
			}
				
		}
		return timesheetById;
	}
	
	private List<Timesheet> removeTimehsheetById(User user, String id) {
		List<Timesheet> timesheetList = currentUser.getTimesheetList();
		for(int i = 0; i < timesheetList.size(); i++) {
			if(timesheetList.get(i).getTimesheetId().equals(id)) {
				timesheetList.remove(i);
				break;
			}
		}
		return timesheetList;
	}

}
