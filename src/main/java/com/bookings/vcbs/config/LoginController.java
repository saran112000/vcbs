package com.bookings.vcbs.config;


import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class LoginController {
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	   @RequestMapping(value = "/login", method = {RequestMethod.GET})
	    public String login(@RequestHeader(name = "User-Agent") String userAgent, Model model, 
				@RequestParam(name = "error", required = false) String error,
	            @RequestParam(name = "logout", required = false) String logout,
				HttpServletRequest req, HttpSession ses, HttpServletResponse response, RedirectAttributes redir) throws Exception{
	    	 logger.info(new Date() +"Inside login ");
	    	 String LoginPage=null;
	    	 try {
			    		 if (error!=null && error.trim() != null)
			    		 {
			    			 model.addAttribute("error", "Your username or password is invalid.");
			    		 }
			    				
			    		 if (logout != null)
		    	    	{
			    			 model.addAttribute("message", "You have been logged out successfully.");
		    	    	}
	    	    	
	    	    /*Code for PMS Acess of Ibas only END*/
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		logger.error(new Date() +" Inside login.htm ");
	    	}
	        return "static/login";
	        
	    }
		
	    @RequestMapping(value = {"/", "/dashboard-page"}, method = RequestMethod.GET)
	    public String welcome(Model model,HttpServletRequest req,HttpSession ses) throws Exception 
	    {
	    	
	    		 
	  	 return "static/dashboard";
	    }
	    
    @RequestMapping(value = {"/sessionExpired","/invalidSession"}, method = RequestMethod.GET)
    public String sessionExpired(Model model,HttpServletRequest req,HttpSession ses,HttpServletResponse resp) {
    	logger.info(new Date() +"Inside sessionExpired ");
    	try 
    	{
    		resp.sendRedirect("/login");
        }
      	catch (Exception e) {
				e.printStackTrace();
			}
    	
        return "SessionExp";
    }
    
   
    
}
