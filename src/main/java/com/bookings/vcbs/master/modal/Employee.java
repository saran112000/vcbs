package com.bookings.vcbs.master.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "labcode", length = 20)
    private String labcode;

    @Column(name = "emp_no", length = 100, unique = true)
    private String empNo;

    @Column(name = "emp_name", length = 255)
    private String empName;

    @Column(name = "desig_id", nullable = false)
    private Long desigId;

    @Column(name = "extension_no", length = 100)
    private String extensionNo;

    @Column(name = "division_id", nullable = false)
    private Long divisionId;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "is_active")
    private Integer isActive = 1;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
