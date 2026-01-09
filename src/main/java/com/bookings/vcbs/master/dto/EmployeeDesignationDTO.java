package com.bookings.vcbs.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeDesignationDTO {

    private Long desigId;
    private String desigCode;
    private String designation;
    
    public EmployeeDesignationDTO(
	    		Long desigId,
	    		String desigCode,
	    		String designation) {
    	
		    	this.desigId = desigId;
		    	this.desigCode = desigCode;
		    	this.designation = designation;
		    }
}
