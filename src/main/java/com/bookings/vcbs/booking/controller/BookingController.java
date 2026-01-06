package com.bookings.vcbs.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {
	
	 @RequestMapping("/bookings")
	    public String welcome(Model model,HttpServletRequest req, HttpSession ses) throws Exception
	    {

	        return "bookings/bookings";
	    }
}