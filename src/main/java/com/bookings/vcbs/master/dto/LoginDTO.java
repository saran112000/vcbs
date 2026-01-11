package com.bookings.vcbs.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    private Long loginId;
    private String username;
    private Long roleId;
    private String roleName;
    private Long empId;
    private String empDetails; 
    private Integer isActive;
    
    public LoginDTO(Long loginId, String username, Long roleId, String roleName, Long empId, String empDetails, Integer isActive){
    	
    	this.loginId = loginId;
    	this.username = username;
    	this.roleId = roleId;
    	this.roleName = roleName;
    	this.empId = empId;
    	this.empDetails = empDetails;
    	this.isActive = isActive;
    }
}