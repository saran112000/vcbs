package com.bookings.vcbs.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDTO;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.PasswordChangeDTO;
import com.bookings.vcbs.master.dto.RoleAccessDTO;
import com.bookings.vcbs.master.dto.RoleSecurityDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.service.MasterService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MasterController {
	
	
	@Autowired
	private MasterService masterService;
	
	@Value("${labcode}")
    private String labcode;
	
	/* ----------------------------------------- Division Details -------------------------------------- */
	
	@GetMapping("/division/list")
    public String divisionList(Model model, HttpSession ses) {
		
		List<EmployeeDivisionDTO> divisionList = masterService.getAllDivisions();
		model.addAttribute("divisionList", divisionList != null ? divisionList : new ArrayList<>());
		
		List<EmployeeDTO> employeeList = masterService.getEmployeeList();
		model.addAttribute("employeeList", employeeList != null ? employeeList : new ArrayList<>());
		
		String roleName = (String)ses.getAttribute("roleName");
		
		List<MainModuleDTO> mainModuleList = masterService.getMainModuleList(roleName);
		model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
		
		List<SubModuleDTO> subModuleList = masterService.getSubModuleList(roleName);
		model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
		
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
    	
    	boolean check = masterService.existsByDivisionCode(divisionDTO.getDivisionCode());
		if(check) {
			redirect.addFlashAttribute("errorMessage", "Division Code " + divisionDTO.getDivisionCode() + " Already Exists!");
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
   
    @GetMapping("/employee/list")
    public String employeeList(Model model, HttpSession ses) {
        List<EmployeeDTO> officerList = masterService.getEmployeeList();
        model.addAttribute("officerList", officerList != null ? officerList : new ArrayList<>());
        
        model.addAttribute("designationList", masterService.getEmployeeDesignationList());
        model.addAttribute("divisionList", masterService.getAllDivisions());
        
    	String roleName = (String)ses.getAttribute("roleName");
		
		List<MainModuleDTO> mainModuleList = masterService.getMainModuleList(roleName);
		model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
		
		List<SubModuleDTO> subModuleList = masterService.getSubModuleList(roleName);
		model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
        
        model.addAttribute("employee", new EmployeeDTO());
        return "masters/employee";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute EmployeeDTO employeeDTO, RedirectAttributes redirect, HttpSession ses, @RequestParam("formAction") String action) throws Exception {
        String userName = (String)ses.getAttribute("userName");
        String labcode = (String)ses.getAttribute("labcode");
        
        if(employeeDTO == null || action == null) {
            redirect.addFlashAttribute("errorMessage", "Employee data is missing!");
            return "redirect:/employee/list";
        }
        
        boolean check = masterService.existsByEmpNo(employeeDTO.getEmpNo());
		if(check) {
			redirect.addFlashAttribute("errorMessage", "Employee Number " + employeeDTO.getEmpNo() + " Already Exists!");
			 return "redirect:/employee/list";
		}
        
        employeeDTO.setLabcode(labcode);
        Long status = masterService.saveEmployee(employeeDTO, userName, action);
        
        String message = action.equalsIgnoreCase("add") ? "added" : "updated";
        if(status != null && status > 0) {
            redirect.addFlashAttribute("successMessage", "Employee " + message + " successfully!");
        } else {
            redirect.addFlashAttribute("errorMessage", "Something went wrong!");
        }
        return "redirect:/employee/list";
    }

    @PostMapping("/employee/delete/{empId}")
    public String deleteEmployee(@PathVariable Long empId, RedirectAttributes redirect, HttpSession ses) {
        String userName = (String)ses.getAttribute("userName");
        Long status = masterService.deleteEmployee(empId, userName);
        
        if(status != null && status > 0) {
            redirect.addFlashAttribute("successMessage", "Employee deactivated successfully!");
        } else {
            redirect.addFlashAttribute("errorMessage", "Error deactivating employee!");
        }
        return "redirect:/employee/list";
    }
    
    
    /* ----------------------------------------- Login Details -------------------------------------- */

    @GetMapping("/login/list")
    public String loginList(Model model, HttpSession ses) {
        List<LoginDTO> loginList = masterService.getAllLogins();
        model.addAttribute("loginList", loginList != null ? loginList : new ArrayList<>());
        
        List<EmployeeDTO> employeeList = masterService.getEmployeeList();
        model.addAttribute("employeeList", employeeList != null ? employeeList : new ArrayList<>());
        
        List<RoleSecurityDTO> roleSecurityList = masterService.getRoleSecurityList();
        model.addAttribute("roleSecurityList", roleSecurityList != null ? roleSecurityList : new ArrayList<>());
        
        String roleName = (String)ses.getAttribute("roleName");
		
		List<MainModuleDTO> mainModuleList = masterService.getMainModuleList(roleName);
		model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
		
		List<SubModuleDTO> subModuleList = masterService.getSubModuleList(roleName);
		model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
		
        model.addAttribute("loginForm", new LoginDTO());
        return "masters/loginList";
    }

    @PostMapping("/login/save")
    public String saveLogin(@ModelAttribute LoginDTO loginDTO, RedirectAttributes redirect, HttpSession ses, @RequestParam("formAction") String action) throws Exception {
        String userName = (String)ses.getAttribute("userName");
        
        if(loginDTO == null || action == null) {
            redirect.addFlashAttribute("errorMessage", "Login data is missing!");
            return "redirect:/login/list";
        }
        
        Long status = masterService.saveLogin(loginDTO, userName, action);
        if(status != null && status > 0) {
            redirect.addFlashAttribute("successMessage", "User " + (action.equalsIgnoreCase("add") ? "added" : "updated") + " successfully!");
        } else {
            redirect.addFlashAttribute("errorMessage", "Something went wrong (Username might already exist)!");
        }
        
        return "redirect:/login/list";
    }
    
    @GetMapping("/login/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestParam String username) {
        return masterService.existsByUsername(username); 
    }

    @PostMapping("/login/delete/{loginId}")
    public String deleteLogin(@PathVariable Long loginId, RedirectAttributes redirect, HttpSession ses) {
        String currentUserName = (String)ses.getAttribute("userName");
        Long status = masterService.deleteLogin(loginId, currentUserName);
        
        if(status != null && status > 0) {
            redirect.addFlashAttribute("successMessage","User deactivated successfully!");
        } else {
            redirect.addFlashAttribute("errorMessage", "Action failed!");
        }
        return "redirect:/login/list";
    }
	
    @GetMapping("/role-access")
    public String roleAccessList(Model model, HttpSession ses) {
    	
    	 String roleName = (String)ses.getAttribute("roleName");
    	
        List<RoleSecurityDTO> roleSecurityList = masterService.getRoleSecurityList();
        model.addAttribute("roleSecurityList", roleSecurityList != null ? roleSecurityList : new ArrayList<>());
        
        List<MainModuleDTO> mainModuleList = masterService.getMainModuleList(roleName);
		model.addAttribute("mainModuleList", mainModuleList != null ? mainModuleList : new ArrayList<>());
		
		List<SubModuleDTO> subModuleList = masterService.getSubModuleList(roleName);
		model.addAttribute("subModuleList", subModuleList != null ? subModuleList : new ArrayList<>());
        
        model.addAttribute("loginForm", new LoginDTO());
        return "masters/roleAccess";
    }
	
    @GetMapping("/role-access/fetch-submodules")
    @ResponseBody
    public List<RoleAccessDTO> getSubModulesForRole(@RequestParam("roleId") Long roleId, 
                                                    @RequestParam("moduleId") Long moduleId) {
        return masterService.getRoleAccessList(roleId, moduleId);
    }

    @PostMapping("/role-access/update")
    @ResponseBody
    public String updateRoleAccess(@RequestBody Map<String, Object> payload, HttpSession ses) {
        try {
            Long roleId = Long.valueOf(payload.get("roleId").toString());
            Long moduleDetailsId = Long.valueOf(payload.get("moduleDetailsId").toString());
            int isActive = Integer.parseInt(payload.get("isActive").toString());
            String userName = (String) ses.getAttribute("userName"); // For audit logs

            masterService.updateRoleAccess(roleId, moduleDetailsId, isActive, userName);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
    
    @PostMapping("/role-access/update-room-type")
    @ResponseBody // <--- This is the missing piece!
    public String updateRoomType(@RequestBody RoleAccessDTO dto, HttpSession session) {
        try {
            // Get username from session since it's not a @RequestBody field
            String username = (String) session.getAttribute("userName"); 
            
            masterService.saveRoomTypes(dto, username);
            
            return "SUCCESS"; // Return the string your JS is looking for
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
    
    @PostMapping("/login/reset-password/{id}")
    public String resetPassword(@PathVariable("id") Long loginId, RedirectAttributes redirect, HttpSession ses) {
        String userName = (String) ses.getAttribute("userName");
        
        boolean success = masterService.resetPassword(loginId, userName);
        
        if (success) {
            redirect.addFlashAttribute("successMessage", "Password reset to '123' successfully!");
        } else {
            redirect.addFlashAttribute("errorMessage", "Failed to reset password.");
        }
        
        return "redirect:/login/list";
    }
    
    @PostMapping("/password/change")
    public String changePassword(@ModelAttribute PasswordChangeDTO passwordDTO, 
                                 HttpSession session, 
                                 RedirectAttributes redirect) {
        
        Long loginId = (Long) session.getAttribute("loginId");
        
        if (loginId == null) {
            return "redirect:/login"; // Session expired
        }

        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            redirect.addFlashAttribute("errorMessage", "New Password and Confirm Password do not match!");
            return "redirect:/dashboard-page";
        }

        try {
            boolean isChanged = masterService.changeUserPassword(loginId, passwordDTO);
            
            if (isChanged) {
                redirect.addFlashAttribute("successMessage", "Password changed successfully!");
            } else {
                redirect.addFlashAttribute("errorMessage", "Incorrect Old Password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirect.addFlashAttribute("errorMessage", "Error occurred while changing password.");
        }

        return "redirect:/dashboard-page";
    }
    
    
}
