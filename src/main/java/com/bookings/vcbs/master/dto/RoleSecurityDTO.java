package com.bookings.vcbs.master.dto;

import lombok.Data;

@Data
public class RoleSecurityDTO {
    private Long roleId; 
    private String roleName;
    
    public RoleSecurityDTO(Long roleId, String roleName) {
    	this.roleId = roleId;
    	this.roleName = roleName;
    }
}