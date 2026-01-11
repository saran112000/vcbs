package com.bookings.vcbs.config;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.service.MasterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class LoginController {

    @Value("${labcode}")
    private String labcode;

    @Autowired
    private MasterService masterService;
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(Model model,
                        @RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Your username or password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "static/login";
    }

    @RequestMapping(value = {"/", "/dashboard-page"}, method = RequestMethod.GET)
    public String welcome(Model model, Principal principal, HttpServletRequest req, HttpSession ses) throws Exception
    {
        LoginDetails loginDetails = masterService.getUserDetails(principal.getName(), labcode);

        if (loginDetails != null) {
            // Setting individual values in Session
            ses.setAttribute("loginId", loginDetails.getLoginId());
            ses.setAttribute("userName", loginDetails.getUserName());
            ses.setAttribute("roleName", loginDetails.getRoleName());
            ses.setAttribute("empId", loginDetails.getEmpId());
            ses.setAttribute("empNo", loginDetails.getEmpNo());
            ses.setAttribute("empName", loginDetails.getEmpName());
            ses.setAttribute("email", loginDetails.getEmail());
            ses.setAttribute("designation", loginDetails.getDesignation());
            ses.setAttribute("divisionName", loginDetails.getDivision_name());
            ses.setAttribute("labcode", labcode);

            model.addAttribute("user", loginDetails);
            
            List<MainModuleDTO> mainModuleList = masterService.getMainModuleList();
    		model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
    		
    		List<SubModuleDTO> subModuleList = masterService.getSubModuleList();
    		model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
        }

        return "static/HeaderSidebar";
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
