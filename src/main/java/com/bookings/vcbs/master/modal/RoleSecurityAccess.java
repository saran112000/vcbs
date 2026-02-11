package com.bookings.vcbs.master.modal;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "role_security_access")
public class RoleSecurityAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_security_access_id")
    private Long roleSecurityAccessId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "module_details_id", nullable = false)
    private Long moduleDetailsId;

    @Column(name = "is_drona", length = 10)
    private String isDrona;
    
    @Column(name = "is_internet", length = 10)
    private String isInternet;

    @Column(name = "is_active", nullable = false)
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