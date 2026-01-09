package com.bookings.vcbs.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private Long loginId;
    private String userName;
    private String password;
    private String roleId;
    private Long empId;
    private String empName; 
    private Integer isActive;
}