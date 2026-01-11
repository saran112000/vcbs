package com.bookings.vcbs.master.modal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "module_details")
public class ModuleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_details_id")
    private Long moduleDetailsId;

    // Relationship to Main Module
    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "module_details_name")
    private String moduleDetailsName;

    @Column(name = "module_details_url")
    private String moduleDetailsUrl;

    @Column(name = "serial_no", nullable = false)
    private Integer serialNo;

    @Column(name = "is_active", nullable = false)
    private Integer isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    // Default Constructor
    public ModuleDetails() {}

    // Getters and Setters
    public Long getModuleDetailsId() { return moduleDetailsId; }
    public void setModuleDetailsId(Long moduleDetailsId) { this.moduleDetailsId = moduleDetailsId; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public String getModuleDetailsName() { return moduleDetailsName; }
    public void setModuleDetailsName(String moduleDetailsName) { this.moduleDetailsName = moduleDetailsName; }

    public String getModuleDetailsUrl() { return moduleDetailsUrl; }
    public void setModuleDetailsUrl(String moduleDetailsUrl) { this.moduleDetailsUrl = moduleDetailsUrl; }

    public Integer getSerialNo() { return serialNo; }
    public void setSerialNo(Integer serialNo) { this.serialNo = serialNo; }

    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }
}
