package com.bookings.vcbs.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDesignationDTO {

    private Long desigId;
    private String desigCode;
    private String designation;
}
