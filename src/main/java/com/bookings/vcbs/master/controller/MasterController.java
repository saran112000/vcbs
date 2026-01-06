package com.bookings.vcbs.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.service.MasterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Controller
public class MasterController {
	
	
	@Autowired
	private MasterService masterService;
	
	@Value("${labcode}")
    private String labcode;
	
	/* ----------------------------------------- Division Details -------------------------------------- */
	
	@GetMapping("/division/list")
    public String listDivisions(Model model) {
		
		List<EmployeeDivisionDTO> divisionList = masterService.getAllDivisions();
		model.addAttribute("divisionList", divisionList != null ? divisionList : new ArrayList<>());
		
		List<EmployeeDTO> employeeList = masterService.getEmployeeList();
		model.addAttribute("employeeList", employeeList != null ? employeeList : new ArrayList<>());
		
        model.addAttribute("division", new EmployeeDivisionDTO());
        return "masters/division";
    }
	
    // CREATE (POST)
    @PostMapping("/division/save")
    public String saveDivision(@ModelAttribute EmployeeDivisionDTO divisionDTO, RedirectAttributes redirect, HttpSession ses, @RequestParam("formAction") String action) throws Exception {
    	
    	String userName = (String)ses.getAttribute("userName");
    	String labcode = (String)ses.getAttribute("labcode");
    	if(divisionDTO == null || action == null)
    	{
    		redirect.addFlashAttribute("errorMessage", "Division data is missing!");
    		return "redirect:/division/list";
    	}
    	
    	divisionDTO.setLabcode(labcode);
    	
    	Long status = masterService.saveDivision(divisionDTO, userName, action);
    	
    	String actionMessage = "";
    	if(action.equalsIgnoreCase("add"))
    	{
    		actionMessage = "added";
    	}
    	else if(action.equalsIgnoreCase("edit"))
    	{
    		actionMessage = "updated";
    	}
    	
    	if(status != null && status > 0) 
    	{
    		redirect.addFlashAttribute("successMessage","Division "+ actionMessage +" successfully!");
    	}
    	else
    	{
    		redirect.addFlashAttribute("errorMessage", "Something went wrong!");
    	}
    	
        return "redirect:/division/list";
    }

    // DELETE
    @PostMapping("/division/delete/{divisionId}")
    public String deleteDivision(@PathVariable Long divisionId, RedirectAttributes redirect, HttpSession ses) {
    	
    	String userName = (String)ses.getAttribute("userName");
    	
    	if(divisionId == null)
    	{
    		redirect.addFlashAttribute("errorMessage", "DivisionId is null!");
    		return "redirect:/division/list";
    	}
    	
        Long status = masterService.deleteDivision(divisionId, userName);
        
        if(status != null && status > 0) 
    	{
    		redirect.addFlashAttribute("successMessage","Division made inactive successfully!");
    	}
    	else
    	{
    		redirect.addFlashAttribute("errorMessage", "Something went wrong!");
    	}
        
        return "redirect:/division/list";
    }
	
	
	
	/* ----------------------------------------- Employee -------------------------------------- */
    
 // LIST PAGE
    @RequestMapping("/employee/employee-list")
    public String getEmployeeList(Model model,
                                  HttpServletRequest req,
                                  HttpSession ses) {
        try {
            req.setAttribute("employeeList", masterService.getEmployeeList());
            model.addAttribute("employee", new Employee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "masters/employeeList";
    }

    // CREATE (POST)
    @PostMapping("/employee/save-employee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        masterService.createEmployee(employee);
        return "redirect:/employee/employee-list";
    }

    // UPDATE (PUT style)
    @PostMapping("/employee/update-employee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        masterService.updateEmployee(employee);
        return "redirect:/employee/employee-list";
    }

    // PATCH (ACTIVE / INACTIVE)
    @PostMapping("/employee/status/{empId}/{status}")
    public String updateEmployeeStatus(@PathVariable Long empId,
                                       @PathVariable Integer status) {
        masterService.updateEmployeeStatus(empId, status);
        return "redirect:/employee/employee-list";
    }

    // DELETE
    @GetMapping("/employee/delete/{empId}")
    public String deleteEmployee(@PathVariable Long empId) {
        masterService.deleteEmployee(empId);
        return "redirect:/employee/employee-list";
    }
    
    
    /* ----------------------------------------- User Manager -------------------------------------- */
	
	@RequestMapping("/login-list")
    public String getLoginDetails(Model model, HttpServletRequest req, HttpSession ses) throws Exception
    {
		try {
			req.setAttribute("UserManagerList", masterService.getUserManagerList());
			model.addAttribute("newUser", new LoginDetails());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return "masters/loginList";
    }
	
	@PostMapping("/save-user")
    public String saveUser(@ModelAttribute("newUser") LoginDetails userDto, RedirectAttributes redirectAttributes) {
        
        try {
        	System.out.println("userDto****"+userDto);
            // Logic to save to database:
            // userService.save(userDto);
            
            redirectAttributes.addFlashAttribute("message", "User saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving user.");
        }

        // Redirect back to the list page so the DataTable refreshes
        return "redirect:/login-list";
    }
	
	
}
