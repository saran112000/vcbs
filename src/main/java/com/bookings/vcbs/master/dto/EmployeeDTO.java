package com.bookings.vcbs.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeDTO {

    private Long empId;
    private String labcode;
    private String empNo;
    private String empName;
    private Long desigId;
    private String extensionNo;
    private Long divisionId;
    private String email;
    private Integer isActive;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;

    private String designationName;
    private String divisionCode;
    private String divisionName;
    
    public EmployeeDTO(
            Long empId,
            String labcode,
            String empNo,
            String empName,
            Long desigId,
            String designationName,
            String extensionNo,
            Long divisionId,
            String divisionCode,
            String divisionName,
            String email
    ) {
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
    }

}


