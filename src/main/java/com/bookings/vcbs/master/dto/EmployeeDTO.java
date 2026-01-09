package com.bookings.vcbs.master.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long empId;
    private String labcode;
    private String empNo;
    private String empName;
    private Long desigId;
    private String designationName;
    private String extensionNo;
    private Long divisionId;
    private String divisionCode;
    private String divisionName;
    private String email;
    private Integer isActive; 

    public EmployeeDTO(Long empId, String labcode, String empNo, String empName, 
                       Long desigId, String designationName, String extensionNo, 
                       Long divisionId, String divisionCode, String divisionName, 
                       String email, Integer isActive) {
        this.empId = empId;
        this.labcode = labcode;
        this.empNo = empNo;
        this.empName = empName;
        this.desigId = desigId;
        this.designationName = designationName;
        this.extensionNo = extensionNo;
        this.divisionId = divisionId;
        this.divisionCode = divisionCode;
        this.divisionName = divisionName;
        this.email = email;
        this.isActive = isActive;
    }
}