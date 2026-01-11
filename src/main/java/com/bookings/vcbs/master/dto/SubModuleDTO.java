package com.bookings.vcbs.master.dto;

import lombok.Data;

@Data
public class SubModuleDTO {
    private Long moduleDetailsId;
    private Long moduleId;
    private String moduleDetailsName;
    private String moduleDetailsUrl;
    private Integer isActive;

    public SubModuleDTO() {}

    public SubModuleDTO(Long moduleDetailsId, Long moduleId, String moduleDetailsName, String moduleDetailsUrl, Integer isActive) {
        this.moduleDetailsId = moduleDetailsId;
        this.moduleId = moduleId;
        this.moduleDetailsName = moduleDetailsName;
        this.moduleDetailsUrl = moduleDetailsUrl;
        this.isActive = isActive;
    }

}