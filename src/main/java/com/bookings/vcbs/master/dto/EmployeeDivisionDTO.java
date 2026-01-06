package com.bookings.vcbs.master.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeDivisionDTO {
   
	private Long divisionId;
    private String divisionCode;
    private String divisionName;
    private Long divisionHeadId;
    private String divisionHeadName;
    private String divisionHeadDesignation;
    private Integer isActive;
    private String labcode;
    
    public EmployeeDivisionDTO(
            Long divisionId,
            String divisionCode,
            String divisionName,
            Long divisionHeadId,
            String divisionHeadName,
            String divisionHeadDesignation,
            Integer isActive) {

        this.divisionId = divisionId;
        this.divisionCode = divisionCode;
        this.divisionName = divisionName;
        this.divisionHeadId = divisionHeadId;
        this.divisionHeadName = divisionHeadName;
        this.divisionHeadDesignation = divisionHeadDesignation;
        this.isActive = isActive;
    }

}

