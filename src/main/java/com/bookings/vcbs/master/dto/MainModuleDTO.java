package com.bookings.vcbs.master.dto;

import lombok.Data;

@Data
public class MainModuleDTO {
    private Long moduleId;
    private String moduleName;
    private String moduleIcon;
    private Integer isActive;

    public MainModuleDTO() {}

    public MainModuleDTO(Long moduleId, String moduleName, String moduleIcon, Integer isActive) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleIcon = moduleIcon;
        this.isActive = isActive;
    }

}
