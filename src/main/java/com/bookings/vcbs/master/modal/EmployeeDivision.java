package com.bookings.vcbs.master.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_division")
@Data
public class EmployeeDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long divisionId;

    @Column(name = "labcode", length = 20)
    private String labcode;

    @Column(name = "division_code", length = 25)
    private String divisionCode;

    @Column(name = "division_name", length = 255)
    private String divisionName;

    @Column(name = "division_head_id", nullable = false)
    private Long divisionHeadId;

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
