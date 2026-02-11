package com.bookings.vcbs.master.dto;

import java.io.Serializable;

public class RoleAccessDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long moduleDetailsId;
    private String moduleDetailsName;
    private boolean hasAccess;
    private String isDrona;
    private String isInternet;
    private Long roleId; 
    private String roomType;

    public RoleAccessDTO() {
    }

    public RoleAccessDTO(Long moduleDetailsId, String moduleDetailsName, Object hasAccessObj, String isDrona, String isInternet) {
        this.moduleDetailsId = moduleDetailsId;
        this.moduleDetailsName = moduleDetailsName;
        
        // Safe conversion for the Boolean/Integer from CASE WHEN
        if (hasAccessObj instanceof Boolean) {
            this.hasAccess = (Boolean) hasAccessObj;
        } else if (hasAccessObj instanceof Number) {
            this.hasAccess = ((Number) hasAccessObj).intValue() == 1;
        } else {
            this.hasAccess = false;
        }

        this.isDrona = (isDrona != null) ? isDrona : "N";
        this.isInternet = (isInternet != null) ? isInternet : "N";

        // Logic to build the roomType string for the frontend
        StringBuilder rt = new StringBuilder();
        if ("Y".equalsIgnoreCase(this.isDrona)) rt.append("Drona");
        if ("Y".equalsIgnoreCase(this.isInternet)) {
            if (rt.length() > 0) rt.append(",");
            rt.append("Internet");
        }
        this.roomType = rt.toString();
    }

    // --- Getters and Setters ---

    public Long getModuleDetailsId() { return moduleDetailsId; }
    public void setModuleDetailsId(Long moduleDetailsId) { this.moduleDetailsId = moduleDetailsId; }

    public String getModuleDetailsName() { return moduleDetailsName; }
    public void setModuleDetailsName(String moduleDetailsName) { this.moduleDetailsName = moduleDetailsName; }

    public boolean isHasAccess() { return hasAccess; }
    public void setHasAccess(boolean hasAccess) { this.hasAccess = hasAccess; }

    public String getIsDrona() { return isDrona; }
    public void setIsDrona(String isDrona) { this.isDrona = isDrona; }

    public String getIsInternet() { return isInternet; }
    public void setIsInternet(String isInternet) { this.isInternet = isInternet; }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

	@Override
	public String toString() {
		return "RoleAccessDTO [moduleDetailsId=" + moduleDetailsId + ", moduleDetailsName=" + moduleDetailsName
				+ ", hasAccess=" + hasAccess + ", isDrona=" + isDrona + ", isInternet=" + isInternet + ", roleId="
				+ roleId + ", roomType=" + roomType + "]";
	}
    
    
}