package com.bookings.vcbs.master.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee_desig")
@Data
public class EmployeeDesignation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desig_id")
    private Long desigId;

    @Column(name = "desig_code", length = 20)
    private String desigCode;

    @Column(name = "designation", length = 255)
    private String designation;
}

