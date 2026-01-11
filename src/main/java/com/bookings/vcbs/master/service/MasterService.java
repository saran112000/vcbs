package com.bookings.vcbs.master.service;

import java.util.List;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDesignationDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDTO;
import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.RoleSecurityDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.projection.LoginProjection;

public interface MasterService {
	
    LoginDetails getUserDetails(String userName, String labcode);

	public List<LoginProjection> getUserManagerList();
	
	public List<EmployeeDivisionDTO> getAllDivisions();

	public EmployeeDivision getDivisionById(Long id);

	public Long saveDivision(EmployeeDivisionDTO division, String userName, String action);

	public Long deleteDivision(Long divisionId, String userName);

	public List<EmployeeDTO> getEmployeeList();

	List<EmployeeDesignationDTO> getEmployeeDesignationList();

	List<EmployeeDTO> getAllEmployees();

	Long saveEmployee(EmployeeDTO dto, String userName, String action);

	Long deleteEmployee(Long empId, String userName);
	
	public List<LoginDTO> getAllLogins();
	
	public Long saveLogin(LoginDTO loginDTO, String userName, String action);
	
	public Long deleteLogin(Long loginId, String userName);

	List<RoleSecurityDTO> getRoleSecurityList();

	boolean existsByUsername(String username);

	boolean existsByDivisionCode(String divisionCode);
	
	boolean existsByEmpNo(String empNo);

	List<MainModuleDTO> getMainModuleList();

	List<SubModuleDTO> getSubModuleList();
	
}
