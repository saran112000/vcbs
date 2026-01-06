package com.bookings.vcbs.master.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookings.vcbs.master.dto.LoginDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OfficerController {
	
	 @RequestMapping("/officer")
	    public String welcome(Model model,HttpServletRequest req, HttpSession ses) throws Exception
	    {

	        return "masters/officer";
	    }
	 
	 @RequestMapping("/Rooms")
	    public String Rooms(Model model,HttpServletRequest req, HttpSession ses) throws Exception
	    {

	        return "masters/rooms";
	    }
}
