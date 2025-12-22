package com.bookings.vcbs.master.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetails {

    private Long loginId;
    private String userName;
    private Long roleId;
    private Long empId;

    private String roleName;

    private String empNo;
    private String empName;
    private String extensionNo;
    private String email;
    private Integer desigId;
    private Integer groupId;

    private String designation;

    private String groupCode;
    private String groupName;
    private Long groupHeadId;
}